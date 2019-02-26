package com.zfoo.orm;

import com.zfoo.orm.entity.MailEnt;
import com.zfoo.orm.entity.PlayerEnt;
import com.zfoo.orm.model.cache.IEntityCaches;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-07 17:07
 */
@Ignore
public class TestInsert {

    // sql
    // SELECT COUNT(*) FROM mailent
    // SELECT COUNT(*) FROM playerent

    private static final Logger logger = LoggerFactory.getLogger(TestInsert.class);

    private static final String CONFIG_LOCATION = "test_orm.xml";

    private static final int PLAYENT_NUM = 1000;

    private static final int MAILENT_NUM = 1000;

    private static final int SLEEP_TIME = 10;

    private static void reportConn(AbstractApplicationContext context) {
        BasicDataSource basicDataSource = (BasicDataSource) context.getBean("dataSource");
        logger.debug("连接[active:{}]，[idle:{}]", basicDataSource.getNumActive(), basicDataSource.getNumIdle());

    }

    private void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_LOCATION);

        IEntityCaches<Long, PlayerEnt> playerEntityCaches = (IEntityCaches<Long, PlayerEnt>) OrmContext.getOrmManager().getEntityCaches(PlayerEnt.class);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < PLAYENT_NUM; i++) {
                PlayerEnt playerEnt = new PlayerEnt(i, (byte) 1, (short) i, i, true, "helloOrm" + i);
                playerEntityCaches.insert(playerEnt);
                sleep();
                logger.debug("thread1执行插入[{}]", i);
                reportConn(context);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < PLAYENT_NUM; i++) {
                PlayerEnt playerEnt = new PlayerEnt(i, (byte) 1, (short) i, i, true, "helloOrm" + i);
                playerEntityCaches.insert(playerEnt);
                sleep();
                logger.debug("thread2执行插入[{}]", i);
                reportConn(context);
            }
        });


        IEntityCaches<String, MailEnt> mailEntityCaches = (IEntityCaches<String, MailEnt>) OrmContext.getOrmManager().getEntityCaches(MailEnt.class);

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < MAILENT_NUM; i++) {
                String mailId = "mail" + i;
                MailEnt mailEnt = new MailEnt(mailId, "jay", "hello" + i);
                mailEntityCaches.insert(mailEnt);
                sleep();
                logger.debug("thread3执行插入[{}]", mailId);
                reportConn(context);
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < MAILENT_NUM; i++) {
                String mailId = "mail" + i;
                MailEnt mailEnt = new MailEnt(mailId, "jay", "hello" + i);
                mailEntityCaches.insert(mailEnt);
                sleep();
                logger.debug("thread4执行插入[{}]", mailId);
                reportConn(context);
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        Thread.sleep(SLEEP_TIME * 4000);
    }

}
