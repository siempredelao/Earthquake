package com.futurice.earthquake.data.repository;

import com.futurice.earthquake.data.model.FeatureEntity;
import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;

public class RepositoryProxy implements Repository {

    private final NetworkDataSource networkDataSource;
    private final MemoryDataSource  memoryDataSource;

    public RepositoryProxy(final MemoryDataSource memoryDataSource, final NetworkDataSource networkDataSource) {
        this.networkDataSource = networkDataSource;
        this.memoryDataSource = memoryDataSource;
    }

    @Override
    public void getEarthquakes(final Callback<GetEarthquakesResponseEntity> callback) {
        if (!memoryDataSource.isDirty()) {
            callback.onSuccess(memoryDataSource.getEarthquakes());
        } else {
            networkDataSource.getEarthquakes(new Callback<GetEarthquakesResponseEntity>() {
                @Override
                public void onSuccess(final GetEarthquakesResponseEntity getEarthquakesResponseEntity) {
                    memoryDataSource.persist(getEarthquakesResponseEntity);
                    callback.onSuccess(getEarthquakesResponseEntity);
                }

                @Override
                public void onFailure(final Throwable throwable) {
                    callback.onFailure(throwable);
                }
            });
        }
    }

    @Override
    public void getEarthquakeById(final String id, final Callback<FeatureEntity> callback) {
        if (!memoryDataSource.isDirty()) {
            callback.onSuccess(memoryDataSource.getEarthquakeById(id));
        } else {
            // Fetch earthquake from server, out of scope of this coding challenge
            callback.onFailure(new Exception());
        }
    }
}
