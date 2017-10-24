package com.sadhika.bmwproject.model;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;

/**
 * Created by Sadhika on 7/4/17.
 */

public class CurrentLocationProvider extends Single implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private SingleObserver mObserver;

    public CurrentLocationProvider(Context ctx) {
        mGoogleApiClient  = new GoogleApiClient.Builder(ctx)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    @SuppressWarnings({"MissingPermission"})
    public Single<Location> getCurrentLocation() {
        return this;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mObserver.onSuccess(location);
            mGoogleApiClient.disconnect();
        }
    }

    @SuppressWarnings({"MissingPermission"})
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            mObserver.onSuccess(location);
            mGoogleApiClient.disconnect();
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mGoogleApiClient.disconnect();
        mObserver.onError(new Throwable("Current Location Error"));
    }

    @SuppressWarnings({"MissingPermission"})
    @Override
    protected void subscribeActual(@NonNull SingleObserver observer) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            observer.onSuccess(location);
        } else {
            this.mObserver = observer;
            mGoogleApiClient.connect();
        }
    }
}
