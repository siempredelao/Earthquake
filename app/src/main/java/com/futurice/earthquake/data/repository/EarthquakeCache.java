package com.futurice.earthquake.data.repository;

import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;

class EarthquakeCache {

    private static final long UPDATE_INTERVAL_IN_MILLIS = 300000L;

    private GetEarthquakesResponseEntity getEarthquakesResponseEntity;

    boolean isDirty() {
        if (getEarthquakesResponseEntity == null || getEarthquakesResponseEntity.getMetadata().getGenerated() == null) {
            return true;
        } else {
            // Data is updated in server every 5 minutes
            return getEarthquakesResponseEntity.getMetadata().getGenerated() + UPDATE_INTERVAL_IN_MILLIS <
                   System.currentTimeMillis();
        }
    }

    void put(final GetEarthquakesResponseEntity getEarthquakesResponseEntity) {
        this.getEarthquakesResponseEntity = getEarthquakesResponseEntity;
    }

    GetEarthquakesResponseEntity get() {
        return getEarthquakesResponseEntity;
    }
}
