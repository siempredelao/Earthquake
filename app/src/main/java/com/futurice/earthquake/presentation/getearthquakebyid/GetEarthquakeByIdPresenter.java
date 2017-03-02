package com.futurice.earthquake.presentation.getearthquakebyid;

import com.futurice.earthquake.domain.getearthquakebyid.GetEarthquakeByIdInteractor;
import com.futurice.earthquake.domain.getearthquakebyid.GetEarthquakeByIdUseCase;
import com.futurice.earthquake.domain.model.Earthquake;
import com.futurice.earthquake.injector.PerActivity;

import javax.inject.Inject;

@PerActivity
public class GetEarthquakeByIdPresenter implements GetEarthquakeByIdMVP.Presenter {

    private final GetEarthquakeByIdUseCase getEarthquakeByIdUseCase;

    private GetEarthquakeByIdMVP.View view;
    private Earthquake earthquake;

    @Inject
    GetEarthquakeByIdPresenter(final GetEarthquakeByIdInteractor getEarthquakeByIdInteractor) {
        this.getEarthquakeByIdUseCase = getEarthquakeByIdInteractor;
    }

    @Override
    public void setView(final GetEarthquakeByIdMVP.View view) {
        this.view = view;
    }

    @Override
    public void start(final String id) {
        view.showLoading();

        getEarthquakeByIdUseCase.execute(id, new GetEarthquakeByIdUseCase.GetEarthquakeByIdCallback() {

            @Override
            public void onLoad(final Earthquake earthquake) {
                // Here we should map Earthquake (domain layer) to EarthquakeModel (presentation layer),
                // so we only use that fields we are interested in
                GetEarthquakeByIdPresenter.this.earthquake = earthquake;
                view.hideLoading();
                view.showEarthquake(earthquake);
            }

            @Override
            public void onEarthquakeNotFoundError() {
                view.hideLoading();
                view.showEarthquakeNotFoundError();
            }

            @Override
            public void onError(final Throwable throwable) {
                view.hideLoading();
                // Throwable should be identified and an appropiate message shown, see GetEarthquakesPresenter
                view.showGenericError();
            }
        });
    }

    @Override
    public void stop() {
        getEarthquakeByIdUseCase.release();
    }

    @Override
    public void openDetails() {
        view.showDetails(earthquake.getUrl());
    }

    @Override
    public void openLocation() {
        view.showLocation(earthquake.getLatitude(), earthquake.getLongitude(), earthquake.getPlace());
    }
}
