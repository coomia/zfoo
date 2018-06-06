package com.zfoo.orm.test;

import com.zfoo.orm.entity.PlayerEnt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-09 10:52
 */
public class TestOrm {
    // sql
    // SELECT COUNT(*) FROM mailent

    private static final Logger logger = LoggerFactory.getLogger(TestOrm.class);


    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");

        MainOrm mainOrm = context.getBean(MainOrm.class);

        List<PlayerEnt> listPlayer = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            PlayerEnt playerEnt = new PlayerEnt(i, (byte) 1, (short) i, i, true, "helloOrm" + i);
            mainOrm.getEntityCaches().insert(playerEnt);
        }


    }

}
