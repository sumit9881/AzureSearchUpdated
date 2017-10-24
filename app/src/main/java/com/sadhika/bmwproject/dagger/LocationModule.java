package com.sadhika.bmwproject.dagger;

import android.content.Context;
import android.location.LocationProvider;

import com.sadhika.bmwproject.model.CurrentLocationProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sadhika on 7/17/17.
 */
@Module
public class LocationModule {
    private Context mContext;
    public LocationModule(Context ctx) {
        mContext = ctx;
    }

    @Singleton
    @Provides
    CurrentLocationProvider provideCurrentLocationProvider() {
        return  new CurrentLocationProvider(mContext);
    }
}
