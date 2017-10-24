package com.sadhika.bmwproject.model;

import android.content.Context;


import com.sadhika.bmwproject.model.database.MySQLiteHelper;
import com.sadhika.bmwproject.model.pojos.LocationInfo;
import com.sadhika.bmwproject.model.retrofit.BMWService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sadhika on 7/3/17.
 */

public class LocationInfoProvider {


    private List<LocationInfo> mLastResult = new ArrayList<>();
    private Context mContext;
    private BMWService mService;

    public LocationInfoProvider(BMWService service, Context ctx) {
        mContext = ctx;
        mService = service;
    }

    public Single<List<LocationInfo>> getLocationsInfo() {

        return Single.concat(locationFromMemory(), locationFromDb(), locationFromInternet())
                .filter(new Predicate<List<LocationInfo>>() {
                    @Override
                    public boolean test(@NonNull List<LocationInfo> list) throws Exception {
                        return list != null && !list.isEmpty();
                    }
                }).first(new ArrayList<LocationInfo>());
    }

    private void writeToDb(final List<LocationInfo> list) {
        MySQLiteHelper helper = new MySQLiteHelper(mContext);
        helper.putAllLocations(list);
    }

    private Single<List<LocationInfo>> locationFromDb() {
        return Single.fromCallable(new Callable<List<LocationInfo>>() {
            @Override
            public List<LocationInfo> call() throws Exception {
                MySQLiteHelper helper = new MySQLiteHelper(mContext);
                return helper.getAllLocations();
            }
        }).doOnSuccess(new Consumer<List<LocationInfo>>() {
            @Override
            public void accept(@NonNull List<LocationInfo> list) throws Exception {
                mLastResult.clear();
                mLastResult.addAll(list);
            }
        });
    }


    private Single<List<LocationInfo>> locationFromMemory() {
        return Single.just(mLastResult);
    }

    private Single<List<LocationInfo>> locationFromInternet() {
        return mService.getLocations().subscribeOn(Schedulers.newThread())
                .doOnSuccess(new Consumer<List<LocationInfo>>() {
                    @Override
                    public void accept(@NonNull List<LocationInfo> locations) throws Exception {
                        writeToDb(locations);
                        mLastResult.clear();
                        mLastResult.addAll(locations);

                    }
                });
    }

}
