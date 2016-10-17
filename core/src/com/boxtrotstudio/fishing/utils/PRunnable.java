package com.boxtrotstudio.fishing.utils;

/**
 * Represents a {@link Runnable} that takes in a single parameter
 * @param <T> The type of the parameter
 */
public interface PRunnable<T> {
    void run(T p);
}
