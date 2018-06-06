package com.zfoo.test;

import com.zfoo.storage.model.anno.Id;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.04 17:32
 */
public class User {
    @Id
    private String id;
    private String name;
    private String sex;
    private int age;

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", sex='" + sex + '\'' + ", age=" + age + '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}