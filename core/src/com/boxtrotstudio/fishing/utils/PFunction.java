package com.boxtrotstudio.fishing.utils;

/**
 * Represents a function that takes in a single value and returns another value
 * @param <T> The type of the parameter
 * @param <R> The type of the return value
 */
public interface PFunction<T, R> {
    R run(T val);
}
