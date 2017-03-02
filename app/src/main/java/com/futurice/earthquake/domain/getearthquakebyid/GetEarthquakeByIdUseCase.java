package com.futurice.earthquake.domain.getearthquakebyid;

import com.futurice.earthquake.domain.model.Earthquake;

import java.util.List;

public interface GetEarthquakeByIdUseCase {

    interface GetEarthquakeByIdCallback {
        void onLoad(Earthquake earthquake);

        void onEarthquakeNotFoundError();

        void onError(Throwable throwable);
    }

    void execute(String earthquakeId, GetEarthquakeByIdCallback callback);

    void release();
}
