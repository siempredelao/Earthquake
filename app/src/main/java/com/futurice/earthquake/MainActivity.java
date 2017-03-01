package com.futurice.earthquake;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.futurice.earthquake.domain.model.Earthquake;
import com.futurice.earthquake.injector.DaggerActivityComponent;
import com.futurice.earthquake.presentation.getearthquakes.GetEarthquakesMVP;
import com.futurice.earthquake.presentation.getearthquakes.GetEarthquakesPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GetEarthquakesMVP.View {

    @Inject
    GetEarthquakesPresenter getEarthquakesPresenter;

    @BindView(R.id.main_activity_recyclerview)
    protected RecyclerView recyclerview;
    @BindView(R.id.main_activity_progressbar)
    protected ProgressBar  progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                               .appComponent(((FutuApplication) getApplication()).getAppComponent())
                               .build()
                               .inject(this);

        getEarthquakesPresenter.setView(this);
        getEarthquakesPresenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getEarthquakesPresenter.stop();
    }

    @Override
    public void showLoading() {
        progressbar.setVisibility(View.VISIBLE);
        recyclerview.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressbar.setVisibility(View.GONE);
        recyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void showList(final List<Earthquake> earthquakeList) {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        final EarthquakeListAdapter adapter = new EarthquakeListAdapter(getEarthquakesPresenter);
        recyclerview.setAdapter(adapter);
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.addAll(earthquakeList);
    }

    @Override
    public void showNoInternetError() {
        showError(R.string.error_message_no_internet);
    }

    @Override
    public void showSlowInternetError() {
        showError(R.string.error_message_slow_internet);
    }

    @Override
    public void showGenericError() {
        showError(R.string.error_message_generic);
    }

    @Override
    public void showEarthquakeById(final String earthquakeId) {
        startActivity(EarthquakeActivity.createIntent(this, earthquakeId));
    }

    private void showError(@StringRes int stringResourceId) {
        Toast.makeText(this, stringResourceId, Toast.LENGTH_SHORT).show();
    }
}
