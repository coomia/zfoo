##Windows下安装kafka
- Kafka强依赖于Zookeeper，必须要先启动zookeeper
```
1.下载，解压并进入Kafka目录，D:\Java\kafka_2.12-0.11.0.0
2.进入config目录找到文件server.properties并打开
3.找到并编辑log.dirs=D:\Kafka\kafka_2.12-0.11.0.0\kafka-logs
5.找到并编辑zookeeper.connect=localhost:2181
6.在cmd控制台执行：  
cd D:\Java\kafka_2.12-0.11.0.0\kafka_2.12-0.11.0.0\bin\windows  
kafka-server-start.bat ../../config/server.properties
7.创建主题： .\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
8.查看主题输入：.\bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
9.创建生产者：.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test
10. 创建消费者：.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
```
