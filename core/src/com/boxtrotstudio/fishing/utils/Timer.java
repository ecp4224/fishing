package com.boxtrotstudio.fishing.utils;

public class Timer {
    private CancelToken cancelToken;
    private Runnable runnable;
    private Thread timerThread;
    private long pauseDuration;

    public static CancelToken newTimer(Runnable runnable, long pauseDuration) {
        Timer timer = new Timer(runnable, pauseDuration);
        timer.start();
        return timer.cancelToken;
    }

    public Timer(Runnable runnable, long pauseDuration) {
        this.runnable = runnable;
        this.pauseDuration = pauseDuration;
        cancelToken = new CancelToken();
        createThread();
    }

    private void createThread() {
        timerThread = new Thread(new TimerRunnable());
    }

    public void start() {
        timerThread.start();
    }

    public void stop() {
        cancelToken.cancel();
        timerThread.interrupt();
    }

    public void stopAndJoin(long timeout) {
        cancelToken.cancel();
        timerThread.interrupt();
        try {
            timerThread.join(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long getPauseDuration() {
        return pauseDuration;
    }

    public CancelToken getCancelToken() {
        return cancelToken;
    }

    private class TimerRunnable implements Runnable {

        @Override
        public void run() {
            while (!cancelToken.isCanceled()) {
                runnable.run();
                try {
                    Thread.sleep(pauseDuration);
                } catch (InterruptedException ignored) { }
            }
        }
    }
}
