package com.futurice.earthquake;

import android.app.Application;

import com.futurice.earthquake.injector.AppComponent;
import com.futurice.earthquake.injector.AppModule;
import com.futurice.earthquake.injector.DaggerAppComponent;

public class FutuApplication extends Application {

    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
