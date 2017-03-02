package com.futurice.earthquake;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futurice.earthquake.domain.model.Earthquake;
import com.futurice.earthquake.presentation.getearthquakes.GetEarthquakesPresenter;

import java.util.ArrayList;
import java.util.List;

class EarthquakeListAdapter extends RecyclerView.Adapter<EarthquakeViewHolder> {

    private final List<Earthquake>        earthquakeList;
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
        final DiffUtil.Callback callback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return earthquakeList.size();
            }

            @Override
            public int getNewListSize() {
                return earthquakes.size();
            }

            @Override
            public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
                return earthquakes.get(newItemPosition).getId().equals(earthquakeList.get(oldItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
                final Earthquake newItem = earthquakes.get(newItemPosition);
                final Earthquake oldItem = earthquakeList.get(oldItemPosition);
                return newItem.getPlace().equals(oldItem.getPlace()) &&
                       newItem.getMagnitude().equals(oldItem.getMagnitude()) &&
                       newItem.getMagnitudeType().equals(oldItem.getMagnitudeType()) &&
                       newItem.getTimestamp().equals(oldItem.getTimestamp());
            }
        };

        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        earthquakeList.clear();
        earthquakeList.addAll(earthquakes);
        diffResult.dispatchUpdatesTo(this);
    }
}
