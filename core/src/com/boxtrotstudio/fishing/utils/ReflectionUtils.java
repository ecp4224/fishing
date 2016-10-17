package com.boxtrotstudio.fishing.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static <T> T getField(Object object, String fieldName) {
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return (T) f.get(object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
