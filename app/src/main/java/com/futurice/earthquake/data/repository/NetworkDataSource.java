package com.futurice.earthquake.data.repository;

import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;

import retrofit2.Response;

public class NetworkDataSource implements Repository {

    private final EarthquakeApiService earthquakeApiService;

    public NetworkDataSource(final EarthquakeApiService earthquakeApiService) {
        this.earthquakeApiService = earthquakeApiService;
    }

    @Override
    public void getEarthquakes(final Callback<GetEarthquakesResponseEntity> callback) {
        try {
            final Response<GetEarthquakesResponseEntity> response = earthquakeApiService.getTodayEarthquakes()
                                                                                        .execute();
            callback.onSuccess(response.body());
        } catch (Exception exception) {
            callback.onFailure(exception);
        }
    }
}
