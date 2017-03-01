package com.futurice.earthquake.data.repository;

import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EarthquakeApiService {

    @GET("all_day.geojson")
    Call<GetEarthquakesResponseEntity> getTodayEarthquakes();

}
