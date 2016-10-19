package com.boxtrotstudio.fishing.utils;

public class Future<T> {
    private T object;
    private boolean isFinished = false;

    public Future() { }

    public void completeFuture(T object) {
        this.object = object;
        isFinished = true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public T getResult() {
        return object;
    }
}
