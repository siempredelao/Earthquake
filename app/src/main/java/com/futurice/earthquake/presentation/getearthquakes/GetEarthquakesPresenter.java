package com.futurice.earthquake.presentation.getearthquakes;

import com.futurice.earthquake.domain.getearthquakes.GetEarthquakesInteractor;
import com.futurice.earthquake.domain.getearthquakes.GetEarthquakesUseCase;
import com.futurice.earthquake.domain.model.Earthquake;
import com.futurice.earthquake.injector.PerActivity;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class GetEarthquakesPresenter implements GetEarthquakesMVP.Presenter {

    private final GetEarthquakesUseCase getEarthquakesUseCase;

    private GetEarthquakesMVP.View view;

    @Inject
    GetEarthquakesPresenter(final GetEarthquakesInteractor getEarthquakesInteractor) {
        this.getEarthquakesUseCase = getEarthquakesInteractor;
    }

    @Override
    public void setView(final GetEarthquakesMVP.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.showLoading();

        getEarthquakesUseCase.execute(new GetEarthquakesUseCase.GetEarthquakesCallback() {
            @Override
            public void onLoad(final List<Earthquake> earthquakeList) {
                view.hideLoading();
                view.showList(earthquakeList);
            }

            @Override
            public void onError(final Throwable throwable) {
                view.hideLoading();
                if (throwable instanceof UnknownHostException) {
                    view.showNoInternetError();
                } else if (throwable instanceof SocketTimeoutException) {
                    view.showSlowInternetError();
                } else {
                    view.showGenericError();
                }
            }
        });
    }

    @Override
    public void stop() {
        getEarthquakesUseCase.release();
    }
}
