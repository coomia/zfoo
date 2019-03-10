package com.zfoo.web.river.storage;

import com.zfoo.storage.model.anno.Id;
import com.zfoo.storage.model.anno.Resource;

import java.util.Arrays;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.04 17:30
 */
@Resource
public class StudentResource {

    @Id
    private String id;
    private String name;
    private int age;
    private String[] courses;
    private User[] users;

    @Override
    public String toString() {
        return "StudentResource{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", age=" + age + ", courses=" + Arrays.toString(courses) + ", users=" + Arrays.toString(users) + '}';
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
}
