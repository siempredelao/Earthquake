package com.futurice.earthquake;

import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.futurice.earthquake.domain.model.Earthquake;
import com.futurice.earthquake.presentation.getearthquakes.GetEarthquakesPresenter;

import java.util.Locale;

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
        tvPlace.setText(earthquake.getPlace());
        tvMagnitude.setText(String.format(Locale.getDefault(),
                                          "Magnitude %.2f %s",
                                          earthquake.getMagnitude(),
                                          earthquake.getMagnitudeType()));
        tvTimestamp.setText(DateUtils.getRelativeTimeSpanString(earthquake.getTimestamp()));
        invalidateBackground(earthquake.getAlert());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onItemClick(earthquake.getId());
            }
        });
    }

    private void invalidateBackground(final String alert) {
        if (alert == null) {
            setBackgroundColor(android.R.color.transparent);
        } else {
            switch (alert) {
                case "green":
                    setBackgroundColor(R.color.alert_green);
                    break;
                case "yellow":
                    setBackgroundColor(R.color.alert_yellow);
                    break;
                case "orange":
                    setBackgroundColor(R.color.alert_orange);
                    break;
                case "red":
                    setBackgroundColor(R.color.alert_red);
                    break;
            }
        }
    }

    private void setBackgroundColor(@ColorRes int color) {
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), color));
    }
}
