package com.boxtrotstudio.fishing.utils;

public class AsyncTask implements Runnable {
    private Runnable runnable;
    private Future<Boolean> future;

    public static Future<Boolean> runTask(Runnable runnable) {
        AsyncTask task = new AsyncTask(runnable);
        task.future = new Future<>();

        Thread thread = new Thread(task);
        thread.start();

        return task.future;
    }

    private AsyncTask(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            runnable.run();
            future.completeFuture(true);
        } catch (Throwable t) {
            t.printStackTrace();
            future.completeFuture(false);
        }
    }
}
