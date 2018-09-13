package com.zfoo.ztest.lambda;

import com.zfoo.util.JsonUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-09-12 20:56
 */
@Ignore
public class StreamTest {

    private class Student {
        private String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private static List<Student> list = new ArrayList<>();
    private static Map<String, Student> map = new HashMap<>();

    {
        list.add(new Student("aaa"));
        list.add(new Student("bbb"));
        list.add(new Student("ccc"));
        map.put("a", new Student("aaa"));
        map.put("b", new Student("bbb"));
        map.put("c", new Student("ccc"));
    }

    @Test
    public void mapTest() {
        // 转换，映射为另一个list
        List<String> stringList = list.stream().map(v -> v.getName()).collect(Collectors.toList());
        System.out.println(JsonUtils.object2String(stringList));

        List<Student> stringList1 = map.entrySet().stream().map(entry -> new Student(entry.getValue().getName())).collect(Collectors.toList());
        System.out.println(JsonUtils.object2String(stringList1));
    }

    @Test
    public void filterTest() {
        // 过滤
        List<String> stringList = list.stream().filter(f -> "aaa".equals(f.getName())).map(v -> v.getName()).collect(Collectors.toList());
        System.out.println(JsonUtils.object2String(stringList));
    }

    @Test
    public void optionalTest() {
        Optional<Student> optionalStudent = list.stream().filter(f -> f.getName().equals("aaa")).findFirst();
        System.out.println(JsonUtils.object2String(optionalStudent.get()));

        Optional.ofNullable(list).orElse(Collections.EMPTY_LIST).forEach(v -> System.out.println(v));

        // 防止集合里为null的yuans
        Optional.ofNullable(list).orElse(Collections.EMPTY_LIST).stream().filter(Objects::nonNull).forEach(v -> System.out.println(v));

        // 去重
        list.stream().distinct().collect(Collectors.toList()).forEach(v -> System.out.println(v));

    }


}
