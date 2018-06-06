package com.zfoo.util.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 14:43
 */
public class RandomSelector<T> {

    private int cursor = 0;

    private List<Element> elementList = new ArrayList<>();

    public boolean addElement(T value, int weight) {
        Element element = new Element();
        element.setValue(value);
        element.setBottom(cursor);
        element.setTop(cursor + weight - 1);
        cursor += weight;
        return elementList.add(element);
    }

    public void clear() {
        elementList.clear();
        cursor = 0;
    }

    public T select() {
        if (cursor <= 0) {
            throw new IllegalStateException("全部的权重是0");
        }
        if (elementList.isEmpty()) {
            throw new IllegalStateException("选择的元素为空");
        }
        Random random = new Random(System.currentTimeMillis());
        long randomNum = random.nextLong() % cursor;
        for (Element element : elementList) {
            if (element.getTop() >= randomNum && randomNum >= element.getBottom()) {
                return element.value;
            }
        }
        return elementList.get(0).getValue();
    }

    public List<T> select(int count) {
        List<T> resultList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            resultList.add(select());
        }
        return resultList;
    }

    private class Element {
        private T value;
        private int bottom;
        private int top;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public int getBottom() {
            return bottom;
        }

        public void setBottom(int bottom) {
            this.bottom = bottom;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }
    }
}
