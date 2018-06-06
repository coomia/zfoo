package com.zfoo.util.property;

import java.io.*;
import java.util.Properties;

/**
 * Properties 作用：读写资源配置文件 键与值只能是字符串 该类继承与HashTable，用法和Map相似<br/>
 * <p>
 * 存储配置文件：<br/>
 * 1.store()，存储的文件名后缀：.properties 加载配置文件：load() <br/>
 * 2.store()，存储的文件名后缀：.xml 加载配置文件：loadFromXML() <br/>
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 11 03 09 59
 */
public class PropertyFile {

    // 创建文件，保存属性
    public void saveProperties() {
        Properties properties = new Properties();
        // 存储
        properties.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
        properties.setProperty("url", "jdbc:oracle:thin:@localhost:1521:orcl");
        properties.setProperty("user", "scott");
        properties.setProperty("password", "tiger");
        // String str=properties.getProperty(key, defaultValue);//获取
        try {
            // 使用绝对路径，盘符开头
            /*
            properties.store(new FileOutputStream(new File(
					"E:/JavaApplication/Instrument/db.properties")),
					"配置文件的说明：db配置");
					*/


            // 使用相对路径，当前的工程文件
            properties.store(new FileOutputStream(
                    new File("utils/src/main/java/com/zfoo/utils/property/db.properties")), "配置文件的说明：db配置");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * 特别注意load()和loadFromXML()的区别
     * 前者加载普通文件，后者加载XML格式文件
     */
    public void loadProperties() {
        Properties properties = new Properties();
        try {
            //类的相对路径，以'/'开头，表示bin目录
            properties.load(new FileInputStream(new File("utils/src/main/java/com/zfoo/utils/property/db.properties")));
            System.out.println(properties.getProperty("driver"));
            System.out.println(properties.getProperty("password"));
            System.out.println(properties.size());
            System.out.println(properties.containsKey("user"));
            System.out.println(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // new PropertiesFile().loadProperties();
        new PropertyFile().saveProperties();
        new PropertyFile().loadProperties();
    }
}
