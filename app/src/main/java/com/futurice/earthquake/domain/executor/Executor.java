package com.futurice.earthquake.domain.executor;

public interface Executor {

    void run(Interactor interactor);

    void stop(Interactor interactor);
}
