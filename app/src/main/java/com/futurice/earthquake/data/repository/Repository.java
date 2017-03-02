package com.futurice.earthquake.data.repository;

import com.futurice.earthquake.data.model.FeatureEntity;
import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;

public interface Repository {

    void getEarthquakes(Callback<GetEarthquakesResponseEntity> callback);

    void getEarthquakeById(String id, Callback<FeatureEntity> callback);

}
