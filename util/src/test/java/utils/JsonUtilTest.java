package utils;

import com.zfoo.util.JsonUtils;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.13 12:24
 */


public class JsonUtilTest {

    public static String id = "\"id\":\"1000\"";
    public static String name = "\"name\":\"jaysunxiao\"";
    public static String sex = "\"sex\":\"boy\"";
    public static String age = "\"age\":22";
    public static String list = "\"list\":[1,2,3]";
    public static String map = "\"map\":{\"1\":1,\"2\":2,\"3\":3}";

    public static String userJson = "{" + id + "," + name + "," + sex + ","
            + age + "," + list + "," + map + "}";

    @Test
    public void string2Object() {
        User user = JsonUtils.string2Object(userJson, User.class);
        System.out.println(user);
    }

    @Test
    public void object2String() {
        User user = new User();
        user.setId("1000");
        user.setName("jaysunxiao");
        user.setSex("boy");
        user.setAge(22);
        //数组，链表，list
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        user.setList(list);
        //map
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        user.setMap(map);
        System.out.println(JsonUtils.object2String(user));
    }

    @Test
    public void string2List() {
        String str = "[1,2,3,4,5,1,1,1,1]";
        List<Integer> list = JsonUtils.string2List(str, Integer.class);
        System.out.println(list);
    }

    @Test
    public void string2Set() {
        String str = "[1,2,3,4,5,1,1,1,1]";
        Set<Integer> set = JsonUtils.string2Set(str, Integer.class);
        System.out.println(set);
    }

    @Test
    public void string2Map() {
        String str = "{\"1\":2,\"2\":3,\"3\":4}";
        Map<Integer, Integer> map = JsonUtils.string2Map(str, Integer.class, Integer.class);
        System.out.println(map);
    }

    @Test
    public void string2Array() {
        String str = "[1,2,3]";
        Integer[] list = JsonUtils.string2Array(str, Integer.class);
        for(Integer integer : list){
            System.out.println(integer);
        }
    }

    @Test
    public void testGetNode() {
        System.out.println(JsonUtils.getNode(userJson, "id"));
    }


    @Test
    public void test() {
        Integer[] list =new Integer[4];
        Class<?> clazz=JsonUtilTest[].class;
        System.out.println(clazz.getName());
    }

}

//@JsonIgnoreProperties({"name", "age"})//可以将它看做是@JsonIgnore的批量操作
class User {
    private String id;
    //@JsonIgnore//作用在字段或方法上，用来完全忽略被注解的字段和方法对应的属性
    //@JsonProperty//注意这里必须得有该注解，因为没有提供对应的getId和setId函数，而是其他的getter和setter，防止遗漏该属性
    private String name;
    private String sex;
    private int age;
    private List<Integer> list;
    private Map<Integer, Integer> map;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", list=" + list +
                ", map=" + map +
                '}';
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

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Integer> map) {
        this.map = map;
    }
}

