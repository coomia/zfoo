package com.zfoo.util;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.File;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 08.29 15:02
 */
public abstract class ClassUtils {

    public static final String FILE_URL_PROTOCOL = "file";

    public static final String JAR_URL_PROTOCOL = "jar";

    public static final String CLASS_SUFFIX = ".class";

    public static final String JAR_SUFFIX = ".jar";

    public static final String ZIP_SUFFIX = ".zip";

    public static final char FILE_SEPARATOR = '/';

    /*
     find the location of the class come from
     1.FileTest.class.getResource("")
     得到的是当前类FileTest.class文件的URI目录。不包括自己！
     如：file:/D:/java/eclipse32/workspace/jbpmtest3/bin/com/test/
     2.FileTest.class.getResource("/")
     　　得到的是当前的classpath的绝对URI路径。
     　　如：file:/D:/java/eclipse32/workspace/jbpmtest3/bin/
     3.Thread.currentThread().getContextClassLoader().getResource("")
     得到的也是当前ClassPath的绝对URI路径。
     　　 如：file:/D:/java/eclipse32/workspace/jbpmtest3/bin/
     4.FileTest.class.getClassLoader().getResource("")
     　　得到的也是当前ClassPath的绝对URI路径。
     　　 如：file:/D:/java/eclipse32/workspace/jbpmtest3/bin/
     5.ClassLoader.getSystemResource("")
     　   得到的也是当前ClassPath的绝对URI路径。
     　　 如：file:/D:/java/eclipse32/workspace/jbpmtest3/bin/
     　我推荐使用Thread.currentThread().getContextClassLoader().getResource("")来得到当前的classpath的绝对路径的URI表示法。
     */

    public static String classLocation(final Class<?> cls) {
        AssertionUtils.notNull(cls);
        URL result = null;
        String clsAsResource = cls.getName().replace(StringUtils.PERIOD, StringUtils.SLASH).concat(CLASS_SUFFIX);
        ProtectionDomain pd = cls.getProtectionDomain();
        if (pd != null) {
            CodeSource cs = pd.getCodeSource();
            if (cs != null) {
                result = cs.getLocation();
            }
            if (result != null) {
                if (FILE_URL_PROTOCOL.equals(result.getProtocol())) {
                    try {
                        // "!/"为分隔符，分割jar包，和jar包里的文件
                        if (result.toExternalForm().endsWith(JAR_SUFFIX) || result.toExternalForm().endsWith(ZIP_SUFFIX)) {
                            result = new URL(JAR_URL_PROTOCOL + StringUtils.COLON + result.toExternalForm() + "!/" + clsAsResource);
                        } else if (new File(result.getFile()).isDirectory()) {
                            result = new URL(result, clsAsResource);
                        }
                    } catch (MalformedURLException ignore) {
                    }
                }
            }
        }
        if (result == null) {
            final ClassLoader clsLoader = cls.getClassLoader();
            result = clsLoader != null ? clsLoader.getResource(clsAsResource) : ClassLoader.getSystemResource(clsAsResource);
        }
        return result.toString();
    }


    private static final String PROJECT_PACKAGE = "org.hotswap";


    /**
     * 获取指定包下的所有类，只能搜索当前项目路径和maven项目路径
     *
     * @param packageName 形如"org.hotswap"，不能带有斜线/，以为java格式为主
     * @return 当前项目下的所有Java类
     * @throws Exception 异常
     */
    public static Set<Class<?>> getAllClasses(String packageName) throws Exception {
        Set<Class<?>> classSet = new LinkedHashSet<>();
        // 定义一个枚举的集合并进行循环来处理这个目录下的things,当前的classpath的绝对路径的URI表示法。
//                file:/D:/MyProfessionalProject/project/Metal/hotswap/target/test-classes/
//                file:/D:/MyProfessionalProject/project/Metal/hotswap/target/classes/
//                file:/D:/MyProfessionalProject/project/Metal/util/target/classes/

        Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(StringUtils.PERIOD, StringUtils.SLASH));
        while (urlEnumeration.hasMoreElements()) {
            // 获取下一个元素，如果是jar：//得到的结果大概是：jar:file:/C:/Users/ibm/.m2/repository/junit/junit/4.12/junit-4.12.jar!/org/junit
            URL url = urlEnumeration.nextElement();
            String protocol = url.getProtocol();
            System.out.println(url);
            if (StringUtils.isBlank(protocol)) {
                continue;
            }
            // file（不打包成jar运行），jar（打包成jar运行）
            if (protocol.equals(FILE_URL_PROTOCOL)) {
                // 获取包的物理路径
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                List<File> fileList = FileUtils.getAllReadableFiles(new File(filePath));
                for (File file : fileList) {
//                    System.out.println(file.getPath());
                    String fileName = file.getName();
                    // 不是.class文件和包含美元符号的内部类或者匿名内部类不算
                    if (!fileName.endsWith(CLASS_SUFFIX) || fileName.contains(StringUtils.DOLLAR)) {
                        continue;
                    }
                    // 如果是java类文件，则去掉后面的.class 只留下类名
                    String className = StringUtils.substringBeforeLast(fileName, CLASS_SUFFIX);
                    if (!StringUtils.isBlank(packageName)) {
                        // D:\\MyProfessionalProject\\project\\Metal\\util\\target\\classes\\com\\zfoo\\util\\AssertionUtils
                        // D:\\MyProfessionalProject\\project\\Metal\\util\\
                        // a = target\\classes\\com\\zfoo\\util\\AssertionUtils
                        String a = StringUtils.substringAfterFirst(file.getAbsolutePath(), FileUtils.getProAbsPath() + File.separator);
                        // a = target.classes.com.zfoo.util.AssertionUtils.class
                        a = a.replaceAll(StringUtils.BACK_SLASH + File.separator, StringUtils.PERIOD);
                        // b = target.classes.
                        String b = StringUtils.substringBeforeFirst(a, packageName);
                        // c = com.zfoo.util.AssertionUtils
                        String c = StringUtils.substringAfterFirst(a, b);
                        // className = com.zfoo.util.AssertionUtils
                        className = StringUtils.substringBeforeLast(c, CLASS_SUFFIX);
                    }
                    // 这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classSet.add(Thread.currentThread().getContextClassLoader().loadClass(className));
                }
            } else if (protocol.equals(JAR_URL_PROTOCOL)) {
                JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                if (jarFile == null) {
                    continue;
                }
                //得到该jar文件下面的类实体
                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                while (jarEntryEnumeration.hasMoreElements()) {
                            /*jarEntryName，无论如何指定包，注意是/符号不是，文件是\，jar是/：
                                    com/***
                                    org/
                                    org/junit/*/
                    JarEntry entry = jarEntryEnumeration.nextElement();
                    String jarEntryName = entry.getName();
                    //这里我们需要过滤不是class文件和不在basePack包名下的类
                    if (!jarEntryName.contains(CLASS_SUFFIX) || jarEntryName.contains(StringUtils.DOLLAR)) {
                        continue;
                    }
                    String className = StringUtils.substringBeforeLast(jarEntryName, CLASS_SUFFIX).replaceAll(StringUtils.SLASH, StringUtils.PERIOD);
//                    System.out.println(className);
                    if (!className.startsWith(packageName)) {
                        continue;
                    }
                    classSet.add(Thread.currentThread().getContextClassLoader().loadClass(className));
                }
            } else {
                FormattingTuple message = MessageFormatter.format("不合法的协议文件[protocol:{}]", protocol);
                throw new IllegalStateException(message.getMessage());
            }
        }
        return classSet;
    }


    public static List<Class<?>> getClasses(String packageName, ClassFilter classFilter) {
        Set<Class<?>> allClasses = null;
        try {
            allClasses = getAllClasses(packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (allClasses == null || allClasses.isEmpty()) {
            return new ArrayList<>();
        }

        List<Class<?>> list = new ArrayList<>();

        if (classFilter == null) {
            list.addAll(allClasses);
            return list;
        }

        for (Class<?> clazz : allClasses) {
            if (classFilter.matches(clazz)) {
                list.add(clazz);
            }
        }

        return list;
    }

    public interface ClassFilter {
        boolean matches(Class<?> clazz);
    }

}
