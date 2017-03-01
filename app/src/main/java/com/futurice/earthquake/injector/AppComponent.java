package com.futurice.earthquake.injector;

import com.futurice.earthquake.data.repository.EarthquakeApiService;
import com.futurice.earthquake.data.repository.Repository;
import com.futurice.earthquake.domain.executor.Executor;
import com.futurice.earthquake.domain.executor.MainThread;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Executor executor();

    MainThread mainThread();

    Repository repository();

    EarthquakeApiService earthquakeApiService();
}
