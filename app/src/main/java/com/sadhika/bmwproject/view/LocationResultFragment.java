package com.sadhika.bmwproject.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sadhika.bmwproject.App;
import com.sadhika.bmwproject.R;
import com.sadhika.bmwproject.model.CurrentLocationProvider;
import com.sadhika.bmwproject.model.pojos.LocationInfo;
import com.sadhika.bmwproject.model.retrofit.BMWService;
import com.sadhika.bmwproject.presenter.LocationResultPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationResultFragment extends Fragment implements LocationResultPresenter.UiUpdater {

    private static final int REQ_CODE = 100;

    @Inject
    BMWService mService;
    @Inject
    Context mContext;
    @Inject
    CurrentLocationProvider mCurrentLocationProvidet;

    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.progressbar)
    ContentLoadingProgressBar mProgressbar;

    private OnInteraction mListener;
    private LocationResultPresenter mPresenter;

    public LocationResultFragment() {
    }

    public static LocationResultFragment newInstance() {
        LocationResultFragment fragment = new LocationResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mPresenter = new LocationResultPresenter(this, mService, mContext, mCurrentLocationProvidet);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.result_fragment_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);
        view.setClickable(true);
        ButterKnife.bind(this, view);
        mList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mList.setAdapter(new MyLocationRecyclerViewAdapter(new ArrayList<LocationInfo>(), mListener));

        mPresenter.fetchLocations();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteraction) {
            mListener = (OnInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void showProgressDialog() {
        mProgressbar.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressbar.hide();
    }

    @Override
    public void updateList(List<LocationInfo> locations) {
        ((MyLocationRecyclerViewAdapter) mList.getAdapter()).updateListItems(locations);
    }

    @Override
    public void showErrorMessage(String msg) {
        new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage(msg).setPositiveButton("Ok", null).create().show();

    }

    @Override
    public void refreshListView() {
        mList.getAdapter().notifyDataSetChanged();
    }


    @Override
    public List<LocationInfo> getRecyclerItems() {
        return ((MyLocationRecyclerViewAdapter) mList.getAdapter()).getItemList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_name:
                mPresenter.sortByName();
                return true;
            case R.id.action_sort_arrival_time:
                mPresenter.sortByArrivalTime();
                return true;
            case R.id.action_sort_distance:
                checkPermissionAndSortByDistance();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkPermissionAndSortByDistance() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQ_CODE);
            return;
        }
        mPresenter.sortByDistance();

    }

    public interface OnInteraction {
        void onListItemClicked(LocationInfo item);
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permission, int[] grantResult) {
        if (reqCode == REQ_CODE) {
            if (grantResult[0] == PackageManager.PERMISSION_GRANTED && grantResult[1] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.sortByDistance();
            }
        }
    }
}
