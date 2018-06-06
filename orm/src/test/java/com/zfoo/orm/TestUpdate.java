package com.zfoo.orm;

import com.zfoo.orm.entity.PlayerEnt;
import com.zfoo.orm.model.cache.IEntityCaches;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-09 10:07
 */
public class TestUpdate {

    // sql
    // SELECT COUNT(*) FROM mailent

    private static final Logger logger = LoggerFactory.getLogger(TestInsert.class);

    public static final String CONFIG_LOCATION = "test_orm.xml";

    public static final int PLAYENT_NUM = 1000;

    public static final int SLEEP_TIME = 10;

    public static void reportConn(AbstractApplicationContext context) {
        BasicDataSource basicDataSource = (BasicDataSource) context.getBean("dataSource");
        logger.debug("连接[active:{}]，[idle:{}]", basicDataSource.getNumActive(), basicDataSource.getNumIdle());

    }

    public static void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_LOCATION);


        IEntityCaches<Long, PlayerEnt> playerEntityCaches = (IEntityCaches<Long, PlayerEnt>) OrmContext.getOrmManager().getEntityCaches(PlayerEnt.class);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<PlayerEnt> listPlayer = new ArrayList<>();
                for (int i = 0; i < PLAYENT_NUM; i++) {
                    PlayerEnt playerEnt = new PlayerEnt(i, (byte) 1, (short) i, i, true, "update" + i);
                    playerEntityCaches.update(playerEnt);
                    sleep();
                    logger.debug("thread1执行更新[{}]", i);
                    reportConn(context);
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<PlayerEnt> listPlayer = new ArrayList<>();
                for (int i = 0; i < PLAYENT_NUM; i++) {
                    PlayerEnt playerEnt = new PlayerEnt(i, (byte) 1, (short) i, i, true, "update" + i);
                    playerEntityCaches.update(playerEnt);
                    sleep();
                    logger.debug("thread2执行更新[{}]", i);
                    reportConn(context);
                }
            }
        });


        thread1.start();
        thread2.start();
    }

}
