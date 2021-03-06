package com.testmonkeys.selenium.wrapper.utils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReflectionUtils {

    public static List<Field> extractFieldsByPredicate(Class<?> type, Predicate<Field> predicate) {
        List<Field> fields = Arrays.asList(getAllFields(type));
        return fields.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static Field[] getAllFields(Class<?> clazz) {
        List<Class<?>> classes = getAllSuperclasses(clazz);
        classes.add(clazz);
        return getAllFields(classes);
    }

    public static Field[] getAllFields(List<Class<?>> classes) {
        Set<Field> fields = new HashSet<>();

        classes.forEach(c -> fields.addAll(Arrays.asList(c.getDeclaredFields())));

        return fields.toArray(new Field[fields.size()]);
    }

    public static List<Class<?>> getAllSuperclasses(Class<?> clazz) {
        ArrayList<Class<?>> classes = new ArrayList<>();

        Class<?> superclass = clazz.getSuperclass();
        while (superclass != null) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes;
    }
}
