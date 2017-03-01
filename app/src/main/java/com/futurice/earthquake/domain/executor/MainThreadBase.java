package com.futurice.earthquake.domain.executor;

import android.os.Handler;
import android.os.Looper;

public class MainThreadBase implements MainThread {

    private final Handler handler;

    public MainThreadBase() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void post(final Runnable runnable) {
        handler.post(runnable);
    }
}
