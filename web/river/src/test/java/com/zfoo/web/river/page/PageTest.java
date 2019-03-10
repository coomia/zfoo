package com.zfoo.web.river.page;

import com.zfoo.util.JsonUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PageTest {


    private static List<String> testList = new ArrayList<>();

    static {
        for (int i = 1; i <= 95; i++) {
            testList.add(String.valueOf(i));
        }
    }

    @Test
    public void testSubList() {
        String[] expecteds = new String[]{"1", "2"};
        Object[] actuals = testList.subList(0, 2).toArray();
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Ignore
    @Test
    public void testPage0() {
        Page page = Page.valueOf(testList, 0, 10);
        System.out.println(JsonUtils.object2String(page));
    }

    @Test
    public void testPage1() {
        Page page = Page.valueOf(testList, 1, 10);
        String[] expecteds = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        Object[] actuals = page.getCurrentPageList().toArray();
        Assert.assertArrayEquals(expecteds, actuals);
        Assert.assertEquals(10, page.totalPage());
    }


    @Test
    public void testPage5() {
        Page page = Page.valueOf(testList, 5, 10);
        String[] expecteds = new String[]{"41", "42", "43", "44", "45", "46", "47", "48", "49", "50"};
        Object[] actuals = page.getCurrentPageList().toArray();
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testPage10() {
        Page page = Page.valueOf(testList, 10, 10);
        String[] expecteds = new String[]{"91", "92", "93", "94", "95"};
        Object[] actuals = page.getCurrentPageList().toArray();
        Assert.assertArrayEquals(expecteds, actuals);
    }
}
