package com.futurice.earthquake.domain.getearthquakes;

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

public class GetEarthquakesInteractor implements Interactor, GetEarthquakesUseCase {

    private final Executor     executor;
    private final MainThread   mainThread;
    private final Repository   repository;
    private final DomainMapper domainMapper;

    private GetEarthquakesCallback callback;

    @Inject
    GetEarthquakesInteractor(final Executor executor,
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
        repository.getEarthquakes(new Callback<GetEarthquakesResponseEntity>() {
            @Override
            public void onSuccess(final GetEarthquakesResponseEntity getEarthquakesResponseEntity) {
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        final List<Earthquake> earthquakeList = domainMapper.transform(getEarthquakesResponseEntity);
                        callback.onLoad(earthquakeList);
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
    public void execute(final GetEarthquakesCallback callback) {
        this.callback = callback;
        executor.run(this);
    }

    @Override
    public void release() {
        executor.stop(this);
    }
}
