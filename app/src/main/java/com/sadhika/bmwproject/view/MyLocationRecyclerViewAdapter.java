package com.sadhika.bmwproject.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sadhika.bmwproject.R;
import com.sadhika.bmwproject.model.pojos.LocationInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLocationRecyclerViewAdapter extends RecyclerView.Adapter<MyLocationRecyclerViewAdapter.ViewHolder> {

    private final List<LocationInfo> mValues;
    private final LocationResultFragment.OnInteraction mListener;

    public MyLocationRecyclerViewAdapter(List<LocationInfo> items, LocationResultFragment.OnInteraction listener) {
        mValues = items;
        mListener = listener;
    }

    public void updateListItems(List<LocationInfo> list) {
        mValues.clear();
        mValues.addAll(list);
        notifyDataSetChanged();
    }

    public List<LocationInfo> getItemList() {
        return mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_location_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mAddress.setText(mValues.get(position).getAddress());
        holder.mLocation.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListItemClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.location_address)
        TextView mAddress;

        @BindView(R.id.location_name)
        TextView mLocation;
        public LocationInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
