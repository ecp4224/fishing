package com.boxtrotstudio.fishing.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for doing basic things with arrays
 */
public class ArrayHelper {

    /**
     * Checks to see if a condition is true for every element in an array. If any item in the array does not meet
     * the condition, then an {@link java.lang.IllegalStateException} will be thrown
     * @param collection The collection to check
     * @param func The condition
     * @param failedMessage What the exception should say when it's false
     * @param <T> The array type
     */
    public static <T> void assertTrueFor(T[] collection, PFunction<T, Boolean> func, String failedMessage) {
        for (T temp : collection) {
            try {
                if (!func.run(temp)) {
                    throw new IllegalStateException(failedMessage);
                }
            } catch (Throwable t) {
                throw new IllegalStateException(failedMessage, t);
            }
        }
    }

    public static <T> List<T> filter(List<T> list, PFunction<T, Boolean> condition) {
        ArrayList<T> toReturn = new ArrayList<>();
        for (T item : list) {
            if (condition.run(item))
                toReturn.add(item);
        }

        return toReturn;
    }

    /**
     * Execute a function for each item in this array
     * @param collection The array to iterate over
     * @param func The function to execute
     * @param <T> The type of this array
     */
    public static <T> void forEach(T[] collection, PRunnable<T> func) {
        for (T temp : collection) {
            func.run(temp);
        }
    }

    /**
     * Combine the contents of two arrays and return the result. Array B will be appended onto array A
     * @param a The first array
     * @param b The second array
     * @param <T> The types of these arrays
     * @return An array with the contents of both A and B
     */
    public static <T> T[] combine(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    /**
     * Checks to see if this array contains an object. This method uses the {@link Object#equals(Object)} function and the
     * {@link Object#hashCode()} function to compare
     * @param a The array to check
     * @param obj The object that should be inside the array
     * @param <T> The type of this array
     * @return True if the item is inside the array, false otherwise
     */
    public static <T> boolean contains(T[] a, T obj)
    {
        for (T item : a) {
            if (item.equals(obj) || item.hashCode() == obj.hashCode())
                return true;
        }

        return false;
    }

    @NotNull
    public static <T> String toString(@NotNull ArrayList<T> arrayList) {
        StringBuilder builder = new StringBuilder();
        for (T d : arrayList) {
            builder.append(d);
            builder.append("\t");
        }

        return builder.toString();
    }
}
