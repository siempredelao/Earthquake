package com.futurice.earthquake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String EARTHQUAKE_ID_EXTRA_KEY = "EARTHQUAKE_ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
    }

    public static Intent createIntent(final Context context, final String earthquakeId) {
        final Intent openEarthquakeActivityIntent = new Intent(context, EarthquakeActivity.class);
        openEarthquakeActivityIntent.putExtra(EARTHQUAKE_ID_EXTRA_KEY, earthquakeId);
        return openEarthquakeActivityIntent;
    }
}
