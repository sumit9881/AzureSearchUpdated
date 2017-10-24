package com.sadhika.bmwproject.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.sadhika.bmwproject.model.CurrentLocationProvider;
import com.sadhika.bmwproject.model.LocationInfoProvider;
import com.sadhika.bmwproject.model.pojos.LocationInfo;
import com.sadhika.bmwproject.model.retrofit.BMWService;
import com.sadhika.bmwproject.utils.Utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LocationResultPresenter {

    private static final String TAG =  LocationResultPresenter.class.getSimpleName();

    private UiUpdater mListener;
    private Context mContext;
    private LocationInfoProvider mLocationProvider;
    private Disposable mCurrentDisposable;
    private CurrentLocationProvider mCurrentLocProvider;

    public LocationResultPresenter(UiUpdater listener, BMWService service, Context context, CurrentLocationProvider locProvider) {
        mListener = listener;
        mLocationProvider = new LocationInfoProvider(service, context);
        mContext = context;
        mCurrentLocProvider = locProvider;
    }

    public void fetchLocations() {
        mListener.showProgressDialog();
        mLocationProvider.getLocationsInfo().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<LocationInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCurrentDisposable = d;
                    }

                    @Override
                    public void onSuccess(@NonNull List<LocationInfo> result) {
                        mListener.hideProgressDialog();
                        mListener.updateList(result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mListener.hideProgressDialog();
                        mListener.showErrorMessage(e.getMessage());
                    }
                });
        ;
    }

    public void sortByName() {
        mListener.showProgressDialog();
        Collections.sort(mListener.getRecyclerItems(), new Comparator<LocationInfo>() {
            @Override
            public int compare(LocationInfo o1, LocationInfo o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        mListener.hideProgressDialog();
        mListener.refreshListView();
    }


    public void sortByDistance() {
        mListener.showProgressDialog();
        mCurrentLocProvider.getCurrentLocation()
                    .subscribe(new SingleObserver<Location>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(final @NonNull Location location) {
                        Collections.sort(mListener.getRecyclerItems(), new Comparator<LocationInfo>() {
                            @Override
                            public int compare(LocationInfo o1, LocationInfo o2) {
                                float dist1 = location.distanceTo(Utils.LocationInfoToLocation(o1));
                                float dist2 = location.distanceTo(Utils.LocationInfoToLocation(o2));
                                return Float.compare(dist1, dist2);
                            }
                        });
                        mListener.hideProgressDialog();
                        mListener.refreshListView();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mListener.showErrorMessage(e.getMessage());
                    }
                });

    }

    public void sortByArrivalTime() {
        mListener.showProgressDialog();
        Collections.sort(mListener.getRecyclerItems(), new Comparator<LocationInfo>() {
            @Override
            public int compare(LocationInfo o1, LocationInfo o2) {
                long arrivalTime1 = Utils.getArrivalTimeStamp(o1.getArrivalTime());
                long arrivalTime2 = Utils.getArrivalTimeStamp(o2.getArrivalTime());
                if (arrivalTime1 == arrivalTime2) {
                    return 0;
                }
                return  arrivalTime1 > arrivalTime2 ? 1 : -1;
            }
        });
        mListener.hideProgressDialog();
        mListener.refreshListView();
    }

    public void onDestroy() {
        if (mCurrentDisposable != null && !mCurrentDisposable.isDisposed()) {
            mCurrentDisposable.dispose();
        }
    }
    public interface UiUpdater {
        void showProgressDialog();
        void hideProgressDialog();
        void updateList(List<LocationInfo> locations);
        void showErrorMessage(String msg);
        void refreshListView();
        List<LocationInfo> getRecyclerItems();
    }

}
