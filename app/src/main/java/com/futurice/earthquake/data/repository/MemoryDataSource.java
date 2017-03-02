package com.futurice.earthquake.data.repository;

import com.futurice.earthquake.data.model.FeatureEntity;
import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;

public class MemoryDataSource {

    private final EarthquakeCache earthquakeCache;

    public MemoryDataSource() {
        this.earthquakeCache = new EarthquakeCache();
    }

    boolean isDirty() {
        return earthquakeCache.isDirty();
    }

    GetEarthquakesResponseEntity getEarthquakes() {
        return earthquakeCache.get();
    }

    void persist(final GetEarthquakesResponseEntity getEarthquakesResponseEntity) {
        earthquakeCache.put(getEarthquakesResponseEntity);
    }

    public FeatureEntity getEarthquakeById(final String id) {
        return earthquakeCache.getById(id);
    }
}
