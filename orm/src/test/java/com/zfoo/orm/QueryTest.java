package com.zfoo.orm;

import com.zfoo.orm.entity.PlayerEnt;
import com.zfoo.orm.model.query.Page;
import org.junit.Ignore;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-29 18:23
 */
@Ignore
public class QueryTest {

    @Test
    public void testQueryAll() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        List<PlayerEnt> list = OrmContext.getQuery().queryAll(PlayerEnt.class);
        System.out.println(list);
    }

    @Test
    public void testNamedQuery() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        List<PlayerEnt> list = (List<PlayerEnt>) OrmContext.getQuery().namedQuery("testQuery", null);
        System.out.println(list);
    }

    @Test
    public void testUniqueNamedQuery() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        PlayerEnt player = (PlayerEnt) OrmContext.getQuery().uniqueQuery("uniqueQuery", (short) 1);
        System.out.println(player);
    }

    @Test
    public void testIndexNamedQuery() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        List<PlayerEnt> list = (List<PlayerEnt>) OrmContext.getQuery().namedQuery("indexQuery", (byte) 1);
        System.out.println(list);
    }

    @Test
    public void testPageQuery() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_orm.xml");
        Page page = Page.valueOf(10, 5);
        List<PlayerEnt> list = (List<PlayerEnt>) OrmContext.getQuery().pagedQuery("pagedQuery", page, 0L);
        System.out.println(list);
    }

}
