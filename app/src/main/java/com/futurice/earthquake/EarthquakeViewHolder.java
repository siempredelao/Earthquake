package com.futurice.earthquake;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.futurice.earthquake.domain.model.Earthquake;
import com.futurice.earthquake.presentation.getearthquakes.GetEarthquakesPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

class EarthquakeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.earthquake_list_item_view_place_textview)
    protected TextView tvPlace;
    @BindView(R.id.earthquake_list_item_view_magnitude_textview)
    protected TextView tvMagnitude;
    @BindView(R.id.earthquake_list_item_view_timestamp_textview)
    protected TextView tvTimestamp;

    private final GetEarthquakesPresenter presenter;

    EarthquakeViewHolder(View itemView, final GetEarthquakesPresenter getEarthquakesPresenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.presenter = getEarthquakesPresenter;

    }

    void bind(final Earthquake earthquake) {
        final EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, itemView.getContext());
        tvPlace.setText(earthquakeViewModel.getPlace());
        tvMagnitude.setText(earthquakeViewModel.getMagnitude());
        tvTimestamp.setText(earthquakeViewModel.getRelativeDateTime());
        itemView.setBackgroundColor(earthquakeViewModel.getAlertColor());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onItemClick(earthquake.getId());
            }
        });
    }
}
