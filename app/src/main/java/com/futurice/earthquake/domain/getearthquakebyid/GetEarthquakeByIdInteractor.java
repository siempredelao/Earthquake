package com.futurice.earthquake.domain.getearthquakebyid;

import com.futurice.earthquake.data.model.FeatureEntity;
import com.futurice.earthquake.data.model.GetEarthquakesResponseEntity;
import com.futurice.earthquake.data.repository.Callback;
import com.futurice.earthquake.data.repository.Repository;
import com.futurice.earthquake.domain.executor.Executor;
import com.futurice.earthquake.domain.executor.Interactor;
import com.futurice.earthquake.domain.executor.MainThread;
import com.futurice.earthquake.domain.mapper.DomainMapper;
import com.futurice.earthquake.domain.model.Earthquake;

import java.util.List;

import javax.inject.Inject;

public class GetEarthquakeByIdInteractor implements Interactor, GetEarthquakeByIdUseCase {

    private final Executor     executor;
    private final MainThread   mainThread;
    private final Repository   repository;
    private final DomainMapper domainMapper;

    private String earthquakeId;
    private GetEarthquakeByIdCallback callback;

    @Inject
    GetEarthquakeByIdInteractor(final Executor executor,
                                final MainThread mainThread,
                                final Repository repository,
                                final DomainMapper domainMapper) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.repository = repository;
        this.domainMapper = domainMapper;
    }

    @Override
    public void run() {
        repository.getEarthquakeById(earthquakeId, new Callback<FeatureEntity>() {
            @Override
            public void onSuccess(final FeatureEntity featureEntity) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        final Earthquake earthquake = domainMapper.transform(featureEntity);
                        if (earthquake != null) {
                            callback.onLoad(earthquake);
                        } else {
                            callback.onEarthquakeNotFoundError();
                        }
                    }
                });
            }

            @Override
            public void onFailure(final Throwable throwable) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(throwable);
                    }
                });
            }
        });
    }

    @Override
    public void execute(final String earthquakeId, final GetEarthquakeByIdCallback callback) {
        this.earthquakeId = earthquakeId;
        this.callback = callback;
        executor.run(this);
    }

    @Override
    public void release() {
        executor.stop(this);
    }
}
