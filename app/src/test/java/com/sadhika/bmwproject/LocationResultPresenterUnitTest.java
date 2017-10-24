package com.sadhika.bmwproject;

import android.content.Context;
import android.location.Location;

import com.sadhika.bmwproject.model.CurrentLocationProvider;
import com.sadhika.bmwproject.model.LocationInfoProvider;
import com.sadhika.bmwproject.model.pojos.LocationInfo;
import com.sadhika.bmwproject.model.retrofit.BMWService;
import com.sadhika.bmwproject.presenter.LocationResultPresenter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.Single;

import static org.mockito.Mockito.when;

/**
 * Created by Sadhika on 7/8/17.
 */

public class LocationResultPresenterUnitTest {

    public List<LocationInfo> mList;
    public List<LocationInfo> mExpectedListNameTest;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    BMWService mBMWService;

    @Mock
    Context mContext;

    @Mock
    Location location;

    @Mock
    CurrentLocationProvider locationProvider;


    @Before
    public void initTest() {



        MockitoAnnotations.initMocks(this);
        buildLocationList();
        buildExpectedNameTestList();
    }



    @Test
    public void testSortByName() {
        LocationResultPresenter presenter = new LocationResultPresenter(new LocationResultPresenter.UiUpdater() {
            @Override
            public void showProgressDialog() {
            }

            @Override
            public void hideProgressDialog() {
            }

            @Override
            public void updateList(List<LocationInfo> locations) {
            }

            @Override
            public void showErrorMessage(String msg) {
            }

            @Override
            public void refreshListView() {
                for (int i = 0 ; i < mList.size(); i++) {
                    Assert.assertEquals(mList.get(i).getName(), mExpectedListNameTest.get(i).getName());
                }
            }

            @Override
            public List<LocationInfo> getRecyclerItems() {
                return mList;
            }
        }, mBMWService,mContext, locationProvider);
        presenter.sortByName();
    }


    @Test
    public void testSortByDistance() {
        location.setLongitude(0);
        location.setLatitude(0);
        when(locationProvider.getCurrentLocation()).thenReturn(Single.just(location));
        when(location.getAltitude()).thenReturn(0.0);
        when(location.getLongitude()).thenReturn(0.0);

        LocationResultPresenter presenter = new LocationResultPresenter(new LocationResultPresenter.UiUpdater() {
            @Override
            public void showProgressDialog() {
            }

            @Override
            public void hideProgressDialog() {
            }

            @Override
            public void updateList(List<LocationInfo> locations) {
            }

            @Override
            public void showErrorMessage(String msg) {
            }

            @Override
            public void refreshListView() {
                for (int i = 0 ; i < mList.size(); i++) {
                    Assert.assertEquals(mList.get(i).getName(), mExpectedListNameTest.get(i).getName());
                }
            }

            @Override
            public List<LocationInfo> getRecyclerItems() {
                return mList;
            }
        }, mBMWService, mContext, locationProvider);
        presenter.sortByDistance();
    }

    private void buildExpectedNameTestList() {
        mExpectedListNameTest = new ArrayList<>();
        LocationInfo loc1 = new LocationInfo();
        loc1.setArrivalTime("2017-20-09T09:41:59.223");
        loc1.setName("aname");
        loc1.setLatitude(11.0);
        loc1.setLongitude(11.0);

        LocationInfo loc2 = new LocationInfo();
        loc2.setArrivalTime("2017-20-09T09:42:59.223");
        loc2.setName("bname");
        loc2.setLatitude(12.0);
        loc2.setLongitude(12.0);

        LocationInfo loc3 = new LocationInfo();
        loc3.setArrivalTime("2017-19-09T09:41:59.223");
        loc3.setName("cname");
        loc3.setLatitude(13.0);
        loc3.setLongitude(13.0);
        mExpectedListNameTest.add(loc1);
        mExpectedListNameTest.add(loc2);
        mExpectedListNameTest.add(loc3);

    }
    private void buildLocationList() {
        mList = new ArrayList<>();
        LocationInfo loc1 = new LocationInfo();
        loc1.setArrivalTime("2017-20-09T09:41:59.223");
        loc1.setName("aname");
        loc1.setLatitude(11.0);
        loc1.setLongitude(11.0);

        LocationInfo loc2 = new LocationInfo();
        loc2.setArrivalTime("2017-20-09T09:42:59.223");
        loc2.setName("cname");
        loc2.setLatitude(13.0);
        loc2.setLongitude(13.0);

        LocationInfo loc3 = new LocationInfo();
        loc3.setArrivalTime("2017-19-09T09:41:59.223");
        loc3.setName("bname");
        loc3.setLatitude(12.0);
        loc3.setLongitude(12.0);
        mList.add(loc1);
        mList.add(loc2);
        mList.add(loc3);
    }
}
