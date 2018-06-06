package com.zfoo.orm;

import com.zfoo.orm.entity.PlayerEnt;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 11 14 13 22
 */
public class AccessorTest {

    @Test
    public void testBatchInsert() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        List<PlayerEnt> listPlayer = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            PlayerEnt playerEnt = new PlayerEnt(i, (byte) 1, (short) i, i, true, "helloOrm" + i);
            listPlayer.add(playerEnt);
        }
        OrmContext.getAccessor().batchInsert(listPlayer);
        OrmContext.getAccessor().batchDelete(listPlayer);
    }

    @Test
    public void testInsert() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        PlayerEnt playerEnt = new PlayerEnt(1, (byte) 2, (short) 3, 5, true, "helloORM");
        OrmContext.getAccessor().insert(playerEnt);
    }

    @Test
    public void testUpdate() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        PlayerEnt playerEnt = new PlayerEnt(3, (byte) 2, (short) 3, 5, true, "helloUpdate");
        OrmContext.getAccessor().update(playerEnt);
    }

    @Test
    public void testBatchUpdate() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        PlayerEnt playerEnt = new PlayerEnt(2, (byte) 2, (short) 3, 5, true, "helloBatchUpdate");
        OrmContext.getAccessor().batchUpdate(Collections.singletonList(playerEnt));
    }

    @Test
    public void testLoad() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        PlayerEnt ent = OrmContext.getAccessor().load(PlayerEnt.class, 1L);
        System.out.println(ent);
    }

    @Test
    public void testRemove() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        OrmContext.getAccessor().delete(PlayerEnt.class, 3L);
    }

    @Test
    public void testBatchRemove() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        List<PlayerEnt> listPlayer = new ArrayList<>();
        for (int i = 0; i <= 500; i++) {
            PlayerEnt ent = OrmContext.getAccessor().load(PlayerEnt.class, (long) i);
            listPlayer.add(ent);
        }
        OrmContext.getAccessor().batchDelete(listPlayer);
    }
}
