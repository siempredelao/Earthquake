package com.futurice.earthquake.injector;

import android.content.Context;

import com.futurice.earthquake.data.repository.EarthquakeApiService;
import com.futurice.earthquake.data.repository.NetworkDataSource;
import com.futurice.earthquake.data.repository.Repository;
import com.futurice.earthquake.domain.executor.Executor;
import com.futurice.earthquake.domain.executor.MainThread;
import com.futurice.earthquake.domain.executor.MainThreadBase;
import com.futurice.earthquake.domain.executor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private static final String ENDPOINT = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/";

    private final Context context;

    public AppModule(final Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context.getApplicationContext();
    }

    @Provides
    @Singleton
    Repository provideRepository(EarthquakeApiService earthquakeApiService) {
        return new NetworkDataSource(earthquakeApiService);
    }

    @Provides
    @Singleton
    MainThread provideMainThread() {
        return new MainThreadBase();
    }

    @Provides
    @Singleton
    Executor provideExecutor() {
        return new ThreadExecutor();
    }

    @Provides
    @Singleton
    EarthquakeApiService provideEarthquakeApiService() {
        return createRetrofitInstance().create(EarthquakeApiService.class);
    }

    private Retrofit createRetrofitInstance() {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(ENDPOINT).build();
    }
}
