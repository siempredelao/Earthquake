package com.futurice.earthquake.presentation.getearthquakebyid;

import com.futurice.earthquake.domain.model.Earthquake;

public interface GetEarthquakeByIdMVP {

    interface Presenter {
        void setView(View view);

        void start(String id);

        void stop();
    }

    interface View {
        void showLoading();

        void hideLoading();

        void showEarthquake(Earthquake earthquake);

        void showEarthquakeNotFoundError();

        void showGenericError();
    }

}
