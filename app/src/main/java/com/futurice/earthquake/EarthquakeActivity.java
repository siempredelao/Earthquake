package com.futurice.earthquake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.futurice.earthquake.domain.model.Earthquake;
import com.futurice.earthquake.injector.DaggerActivityComponent;
import com.futurice.earthquake.presentation.getearthquakebyid.GetEarthquakeByIdMVP;
import com.futurice.earthquake.presentation.getearthquakebyid.GetEarthquakeByIdPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarthquakeActivity extends AppCompatActivity implements GetEarthquakeByIdMVP.View {

    public static final String EARTHQUAKE_ID_EXTRA_KEY = "EARTHQUAKE_ID_KEY";

    @Inject
    GetEarthquakeByIdPresenter getEarthquakeByIdPresenter;

    @BindView(R.id.earthquake_activity_place_textview)
    protected TextView    tvPlace;
    @BindView(R.id.earthquake_activity_magnitude_textview)
    protected TextView    tvMagnitude;
    @BindView(R.id.earthquake_activity_location_textview)
    protected TextView    tvLocation;
    @BindView(R.id.earthquake_activity_depth_textview)
    protected TextView    tvDepth;
    @BindView(R.id.earthquake_activity_time_textview)
    protected TextView    tvTime;
    @BindView(R.id.earthquake_activity_alert_textview)
    protected TextView    tvAlert;
    @BindView(R.id.earthquake_activity_content_scrollview)
    protected ScrollView  svContent;
    @BindView(R.id.earthquake_activity_progressbar)
    protected ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        ButterKnife.bind(this);

        final String earthquakeId = getIntent().getStringExtra(EARTHQUAKE_ID_EXTRA_KEY);

        DaggerActivityComponent.builder()
                               .appComponent(((FutuApplication) getApplication()).getAppComponent())
                               .build()
                               .inject(this);

        getEarthquakeByIdPresenter.setView(this);
        getEarthquakeByIdPresenter.start(earthquakeId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getEarthquakeByIdPresenter.stop();
    }

    @Override
    public void showLoading() {
        svContent.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        svContent.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showEarthquake(final Earthquake earthquake) {
        final EarthquakeViewModel earthquakeViewModel = new EarthquakeViewModel(earthquake, getApplicationContext());
        tvPlace.setText(earthquakeViewModel.getPlace());
        tvMagnitude.setText(earthquakeViewModel.getMagnitude());
        tvLocation.setText(earthquakeViewModel.getLocation());
        tvDepth.setText(earthquakeViewModel.getDepth());
        tvTime.setText(earthquakeViewModel.getFormattedDateTime());
        tvAlert.setText(earthquakeViewModel.getAlert());
        tvAlert.setBackgroundColor(earthquakeViewModel.getAlertColor());
    }

    @Override
    public void showEarthquakeNotFoundError() {
        Toast.makeText(this, R.string.error_message_earthquake_not_found, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showGenericError() {
        Toast.makeText(this, R.string.error_message_generic, Toast.LENGTH_SHORT).show();
        finish();
    }

    public static Intent createIntent(final Context context, final String earthquakeId) {
        final Intent openEarthquakeActivityIntent = new Intent(context, EarthquakeActivity.class);
        openEarthquakeActivityIntent.putExtra(EARTHQUAKE_ID_EXTRA_KEY, earthquakeId);
        return openEarthquakeActivityIntent;
    }
}
