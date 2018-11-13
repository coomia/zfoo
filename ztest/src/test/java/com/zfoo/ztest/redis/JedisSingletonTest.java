package com.zfoo.ztest.redis;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/13
 */
public class JedisSingletonTest {

    @Test
    public void testJedisConnection() {
        //创建一个jedis的对象。
        Jedis jedis = new Jedis("localhost", 6379);
        // jedis.auth("admin"); // 密码验证
        //调用jedis对象的方法，方法名称和redis的命令一致。
        jedis.set("key", "jedis test"); // myKey:key，':'区分层级关系
        String string = jedis.get("key");
        System.out.println(string);
        //关闭jedis。
        jedis.close();
    }


    /**
     * 使用连接池
     */
    @Test
    public void testJedisPool() {
        //创建jedis连接池
        JedisPool pool = new JedisPool("localhost", 6379);
        //从连接池中获得Jedis对象
        Jedis jedis = pool.getResource();
        String string = jedis.get("key");
        System.out.println(string);
        //关闭jedis对象
        jedis.close();
        pool.close();
    }

    @Test
    public void testRedisSingleton() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:redis/redis-singleton.xml");
        JedisPool pool = context.getBean(JedisPool.class);
        Jedis jedis = pool.getResource();
        String string = jedis.get("key");
        System.out.println(string);
        jedis.close();
        pool.close();
    }


}
