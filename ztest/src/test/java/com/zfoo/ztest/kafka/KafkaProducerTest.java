package com.zfoo.ztest.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-08-05 03:02
 */
public class KafkaProducerTest implements Runnable {

    private final KafkaProducer<String, String> producer;
    private final String topic;

    /**
     * 生产者的缓冲空间池保留尚未发送到服务器的消息，后台I/O线程负责将这些消息转换成请求发送到集群。如果使用后不关闭生产者，则会泄露这些资源。
     *
     * @param topicName
     */
    public KafkaProducerTest(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");// ack是判别请求是否为完整的条件（就是是判断是不是成功发送了）。我们指定了“all”将会阻塞消息，这种设置性能最低，但是是最可靠的。
        props.put("retries", 0);// 如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性。
        props.put("batch.size", 16384);//(生产者)缓存每个分区未发送消息。缓存的大小是通过 batch.size 配置指定的。
        // 控制生产者可用的缓存总量，如果消息发送速度比其传输到服务器的快，将会耗尽这个缓存空间。当缓存空间耗尽，其他发送调用将被阻塞，阻塞时间的阈值通过max.block.ms设定，之后它将抛出一个TimeoutException。
        props.put("buffer.memory", 33554432);
        // 将用户提供的key和value对象ProducerRecord转换成字节，你可以使用附带的ByteArraySerializaer或StringSerializer处理简单的string或byte类型。
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("producer.type", "async");
        this.producer = new KafkaProducer<>(props);
        this.topic = topicName;
    }

    @Override
    public void run() {
        int messageNo = 1;
        try {
            for (; ; ) {
                String messageStr = "你好，这是第" + messageNo + "条数据";
                producer.send(new ProducerRecord<>(topic, "Message", messageStr));
                //生产了100条就打印
                if (messageNo % 100 == 0) {
                    System.out.println("发送的信息:" + messageStr);
                }
                //生产1000条就退出
                if (messageNo % 1000 == 0) {
                    System.out.println("成功发送了" + messageNo + "条");
                    break;
                }
                messageNo++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    public static void main(String args[]) {
        KafkaProducerTest test = new KafkaProducerTest("KAFKA_TEST");
        Thread thread = new Thread(test);
        thread.start();
    }
}