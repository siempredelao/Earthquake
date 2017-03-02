package com.futurice.earthquake;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;

import com.futurice.earthquake.domain.model.Earthquake;

public class EarthquakeViewModel {

    static final String GREEN_ALERT  = "green";
    static final String YELLOW_ALERT = "yellow";
    static final String ORANGE_ALERT = "orange";
    static final String RED_ALERT    = "red";

    private final Earthquake earthquake;
    private final Context    context;

    public EarthquakeViewModel(final Earthquake earthquake, final Context context) {
        this.earthquake = earthquake;
        this.context = context;
    }

    public String getPlace() {
        return earthquake.getPlace();
    }

    public String getMagnitude() {
        if (earthquake.getMagnitude() == null || earthquake.getMagnitudeType() == null) {
            return context.getString(R.string.earthquake_magnitude_not_available_message);
        } else {
            return context.getString(R.string.earthquake_magnitude_message,
                                     earthquake.getMagnitude(),
                                     earthquake.getMagnitudeType());
        }
    }

    public String getLocation() {
        return context.getString(R.string.earthquake_location_message,
                                 earthquake.getLatitude(),
                                 earthquake.getLongitude());
    }

    public String getDepth() {
        return context.getString(R.string.earthquake_depth_message, earthquake.getDepth());
    }

    public String getFormattedDateTime() {
        return DateUtils.formatDateTime(context, earthquake.getTimestamp(), 0);
    }

    public String getRelativeDateTime() {
        return DateUtils.getRelativeTimeSpanString(earthquake.getTimestamp()).toString();
    }

    public int getAlert() {
        final String alert = earthquake.getAlert();
        if (alert == null) {
            return R.string.alert_none_message;
        } else {
            switch (alert) {
                case GREEN_ALERT:
                    return R.string.alert_green_message;
                case YELLOW_ALERT:
                    return R.string.alert_yellow_message;
                case ORANGE_ALERT:
                    return R.string.alert_orange_message;
                case RED_ALERT:
                    return R.string.alert_red_message;
            }
        }
        return 0;
    }

    public int getAlertColor() {
        final String alert = earthquake.getAlert();
        if (alert == null) {
            return getColor(android.R.color.transparent);
        } else {
            switch (alert) {
                case GREEN_ALERT:
                    return getColor(R.color.alert_green);
                case YELLOW_ALERT:
                    return getColor(R.color.alert_yellow);
                case ORANGE_ALERT:
                    return getColor(R.color.alert_orange);
                case RED_ALERT:
                    return getColor(R.color.alert_red);
            }
        }
        return 0;
    }

    private int getColor(int transparent) {
        return ContextCompat.getColor(context, transparent);
    }
}
