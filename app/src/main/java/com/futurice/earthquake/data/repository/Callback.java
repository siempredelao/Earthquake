package com.futurice.earthquake.data.repository;

public interface Callback<T> {

    void onSuccess(T t);

    void onFailure(Throwable throwable);

}
