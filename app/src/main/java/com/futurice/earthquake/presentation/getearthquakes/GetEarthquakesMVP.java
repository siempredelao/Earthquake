package com.futurice.earthquake.presentation.getearthquakes;

import com.futurice.earthquake.domain.model.Earthquake;

import java.util.List;

public interface GetEarthquakesMVP {

    interface Presenter {
        void setView(View view);

        void start();

        void stop();
    }

    interface View {
        void showLoading();

        void hideLoading();

        void showList(List<Earthquake> earthquakeList);

        void showNoInternetError();

        void showSlowInternetError();

        void showGenericError();
    }

}
