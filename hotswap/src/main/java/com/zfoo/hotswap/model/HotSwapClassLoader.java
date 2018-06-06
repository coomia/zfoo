package com.zfoo.hotswap.model;

import com.zfoo.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * * 可以编译动态java文件
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-12 12:23
 */
public class HotSwapClassLoader extends ClassLoader {

    // 设定要搜索类的路径，可以是目录，jar文件，zip文件（里面都是class文件），会覆盖掉所有在CLASSPATH里面的设定。
    // javac  -classpath D:\MyProfessionalProject\project\Metal\hotswap\target\test-classes D:\MyProfessionalProject\project\Metal\hotscript\*.java


    // 设定要搜索编译所需java 文件的路径，可以是目录，jar文件，zip文件（里面都是java文件）。
    // javac  -sourcepath D:\MyProfessionalProject\project\Metal\hotswap\src\test\java D:\MyProfessionalProject\project\Metal\hotscript\*.java

    // 设定要搜索类的路径，可以是目录，jar文件，zip文件（里面都是class文件），会覆盖掉所有在CLASSPATH里面的设定
    private static final String CLASS_PATH = "-classpath ";
    private static final String CLASS_PATH_VALUE = "D:\\MyProfessionalProject\\project\\Metal\\hotswap\\target\\test-classes ";
    // 设定要搜索编译所需java 文件的路径，可以是目录，jar文件，zip文件（里面都是java文件）
    private static final String SOURCE_PATH = "-sourcepath ";
    private static final String SOURCE_PATH_VALUE = "D:\\MyProfessionalProject\\project\\Metal\\hotswap\\src\\test\\java D:\\MyProfessionalProject\\project\\Metal\\hotscript\\*.java ";


    Map<String, ClassFileDef> updateClassMap;

    public HotSwapClassLoader() {

    }

    public void setUpdateClassMap(Map<String, ClassFileDef> updateClassMap) {
        this.updateClassMap = updateClassMap;
    }

    // 定义编译指定Java文件的方法
    public boolean compileJavaFile(File file) throws IOException, InterruptedException {
        // 调用系统的javac命令
        Process p = Runtime.getRuntime().exec("javac " + CLASS_PATH + CLASS_PATH_VALUE + file.getAbsolutePath());
        // 其他线程都等待这个线程完成
        p.waitFor();
        // 获取javac线程的退出值
        int ret = p.exitValue();
        // 返回编译是否成功
        return ret == 0;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;

        ClassFileDef classFileDef = updateClassMap.get(name);
        byte[] data = classFileDef.getData();
        if (data == null) {
            throw new ClassNotFoundException(name);
        }

        // 不同的类加载器加载的即使是同一个data，也默认不是同一个类
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            Method method = ClassLoader.class.getDeclaredMethod("defineClass"
                    , String.class, byte[].class, int.class, int.class);
            ReflectionUtils.makeAccessible(method);
            clazz = (Class<?>) ReflectionUtils.invokeMethod(classLoader, method
                    , classFileDef.getClassName(), data, 0, data.length);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return clazz;
    }

}
