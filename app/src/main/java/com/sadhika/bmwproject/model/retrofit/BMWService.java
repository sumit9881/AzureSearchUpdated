package com.sadhika.bmwproject.model.retrofit;

import com.sadhika.bmwproject.model.pojos.LocationInfo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Sadhika on 7/1/17.
 */

public interface BMWService {
    @GET("/api/Locations")
    Single<List<LocationInfo>> getLocations();
}
