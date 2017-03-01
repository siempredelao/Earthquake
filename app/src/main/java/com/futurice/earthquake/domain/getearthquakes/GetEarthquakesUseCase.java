package com.futurice.earthquake.domain.getearthquakes;

import com.futurice.earthquake.domain.model.Earthquake;

import java.util.List;

public interface GetEarthquakesUseCase {

    interface GetEarthquakesCallback {
        void onLoad(List<Earthquake> earthquakeList);

        void onError(Throwable throwable);
    }

    void execute(GetEarthquakesCallback callback);

    void release();
}
