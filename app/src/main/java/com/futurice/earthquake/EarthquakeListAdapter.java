package com.futurice.earthquake;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futurice.earthquake.domain.model.Earthquake;
import com.futurice.earthquake.presentation.getearthquakes.GetEarthquakesPresenter;

import java.util.ArrayList;
import java.util.List;

class EarthquakeListAdapter extends RecyclerView.Adapter<EarthquakeViewHolder> {

    private final List<Earthquake> earthquakeList;
    private final GetEarthquakesPresenter getEarthquakesPresenter;

    EarthquakeListAdapter(final GetEarthquakesPresenter getEarthquakesPresenter) {
        this.earthquakeList = new ArrayList<>();
        this.getEarthquakesPresenter = getEarthquakesPresenter;
    }

    @Override
    public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.view_earthquake_list_item, parent, false);
        return new EarthquakeViewHolder(view, getEarthquakesPresenter);
    }

    @Override
    public void onBindViewHolder(EarthquakeViewHolder holder, int position) {
        holder.bind(earthquakeList.get(position));
    }

    @Override
    public int getItemCount() {
        return earthquakeList.size();
    }

    void addAll(final List<Earthquake> earthquakes) {
        final int previousSize = earthquakeList.size();
        earthquakeList.addAll(earthquakes);
        notifyItemRangeInserted(previousSize, earthquakes.size());
    }
}
