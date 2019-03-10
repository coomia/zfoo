package com.zfoo.util;

import com.zfoo.util.exception.POJOException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.11 11:51
 */
public abstract class ReflectionUtils {

    /**
     * 如果field符合FieldFilter过滤条件，则执行回调方法
     *
     * @param field 属性
     * @param ff    属性过滤器
     * @param fc    属性回调方法
     */
    public static void filterField(Field field, FieldFilter ff, FieldCallback fc) {
        if (ff != null && !ff.matches(field)) {
            return;
        }
        try {
            fc.callBack(field);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
        }
    }

    /**
     * 将clazz通过filter过滤，过滤后的field执行callback方法
     * <p>
     * Invoke the given callback on all fields in the target class, going up the
     * class hierarchy to get all declared fields.
     * </p>
     *
     * @param clazz the target class to analyze
     * @param fc    the callback to invoke for each field
     * @param ff    the matches that determines the fields to apply the callback to
     */
    public static void filterFieldsInClass(Class<?> clazz, FieldFilter ff, FieldCallback fc) {
        // Keep backing up the inheritance hierarchy.
        Class<?> targetClass = clazz;
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                ReflectionUtils.filterField(field, ff, fc);
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
    }

    public static boolean isPOJOClass(Class<?> clazz) {
        if (!clazz.getSuperclass().equals(Object.class)) {
            return false;
        }
        return true;
    }

    public static void assertIsPOJOClass(Class<?> clazz) {
        if (!isPOJOClass(clazz)) {
            throw new POJOException(clazz.getSimpleName() + "不是简单的javabean");
        }
    }

    /**
     * 从一个指定的POJO的Class中获得具有指定注解的Field，只获取子类的Field，不获取父类的Field
     *
     * @param clazz      指定的Class
     * @param annotation 指定注解的Class
     * @return 数组，可能长度为0
     */
    public static Field[] getFieldsByAnnoInPOJOClass(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotation)) {
                list.add(field);
            }
        }
        return list.toArray(new Field[list.size()]);
    }

    /**
     * 从一个Class中获得具有指定注解的Method，只获取子类的Method，不获取父类的Method
     *
     * @param clazz      指定的Class
     * @param annotation 指定注解的Class
     * @return 数组，可能长度为0
     */
    public static Method[] getMethodsByAnnoInPOJOClass(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Method> list = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                list.add(method);
            }
        }
        return list.toArray(new Method[list.size()]);
    }

    public static Method[] getMethodsByNameInPOJOClass(Class<?> clazz, String methodName) {
        List<Method> list = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase(methodName)) {
                list.add(method);
            }
        }
        return list.toArray(new Method[list.size()]);
    }

    /**
     * Attempt to get all Methods on the supplied class.
     * Searches all superclasses up to {@code Object}.
     *
     * @param clazz the class to introspect
     * @return 数组，可能长度为0
     */
    public static Method[] getAllMethods(Class<?> clazz) {
        AssertionUtils.notNull(clazz, "Class must not be null");
        List<Method> list = new ArrayList<>();
        Class<?> superClazz = clazz;
        while (superClazz != null) {
            Method[] methods = superClazz.getDeclaredMethods();
            for (Method method : methods) {
                list.add(method);
            }
            superClazz = superClazz.getSuperclass();
        }
        return list.toArray(new Method[list.size()]);
    }
    //*************************************操作Class*********************************

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(clazz + "无法被实例化");
        }
    }

    public static <T> T newInstance(Constructor<T> constructor) {
        try {
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(constructor + "无法被实例化");
        }
    }


    /**
     * 等于{@link Field#get(Object)}
     * <p>
     * In accordance with {@link Field#get(Object)}
     * semantics, the returned value is automatically wrapped if the underlying field
     * has a primitive type.
     * </p>
     *
     * @param field  the field to get
     * @param target the target object from which to get the field
     * @return the field's current value
     */
    public static Object getField(Field field, Object target) {
        try {
            return field.get(target);
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected reflection exception - " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * 等于{@link Field#set(Object, Object)}
     * <p>
     * In accordance with {@link Field#set(Object, Object)} semantics, the new value
     * is automatically unwrapped if the underlying field has a primitive type.
     * </p>
     *
     * @param field  the field to set
     * @param target the target object on which to set the field
     * @param value  the value to set; may be {@code null}
     */
    public static void setField(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected reflection exception - " + e.getClass().getName() + ": " + e.getMessage());
        }
    }


    /**
     * Invoke the specified {@link Method} against the supplied target object with the
     * supplied arguments. The target object can be {@code null} when invoking a
     * static {@link Method}.
     *
     * @param target the target object to invoke the method on
     * @param method the method to invoke
     * @param args   the invocation arguments (may be {@code null})
     * @return the invocation result, if any
     */
    public static Object invokeMethod(Object target, Method method, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected reflection exception - " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * 让私有变量可访问，在必要的情况下调用
     * <p>
     * Make the given field accessible, explicitly setting it accessible if necessary.
     * </p>
     *
     * @param field the field to make accessible
     * @see Field#setAccessible
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * Make the given method accessible
     *
     * @param method the method to make accessible
     * @see Method#setAccessible
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * Make the given constructor accessible, explicitly setting it accessible
     * if necessary. The {@code setAccessible(true)} method is only called
     * when actually necessary, to avoid unnecessary conflicts with a JVM
     * SecurityManager (if active).
     *
     * @param constructor the constructor to make accessible
     * @see Constructor#setAccessible
     */
    public static void makeAccessible(Constructor<?> constructor) {
        if ((!Modifier.isPublic(constructor.getModifiers()) || !Modifier.isPublic(constructor.getDeclaringClass().getModifiers())) && !constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
    }


    /**
     * 属性过滤器
     */
    public interface FieldFilter {
        boolean matches(Field field);
    }

    /**
     * Callback interface invoked on each field in the hierarchy.
     */
    public interface FieldCallback {
        void callBack(Field field) throws IllegalArgumentException, IllegalAccessException;
    }


}

