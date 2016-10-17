package com.boxtrotstudio.fishing.utils;

/**
 * A CancelToken is a token that indicates whether a task should be canceled or not.
 */
public class CancelToken {
    private volatile boolean isCanceled;
    private Runnable onCancel;

    public CancelToken() { }
    public CancelToken(Runnable onCancel) {
        this.onCancel = onCancel;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void cancel() {
        isCanceled = true;

        if (onCancel != null)
            onCancel.run();
    }

    public void setCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;

        if (isCanceled && onCancel != null)
            onCancel.run();
    }
}
