package com.zfoo.ztest.redis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/13
 */
public class JedisClusterTest {

    @Test
    public void testJedisCluster() throws IOException {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("localhost", 6379));

        JedisCluster cluster = new JedisCluster(nodes);

        cluster.set("key", "1000");
        String string = cluster.get("key");
        System.out.println(string);

        cluster.close();
    }

    @Test
    public void testRedisCluster() throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:redis/redis-cluster.xml");
        JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("jedisCluster");
        String string = jedisCluster.get("key");
        System.out.println(string);
        jedisCluster.close();
    }


}
