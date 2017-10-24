package com.sadhika.bmwproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sadhika.bmwproject.R;
import com.sadhika.bmwproject.model.pojos.LocationInfo;

public class MainActivity extends AppCompatActivity implements LocationResultFragment.OnInteraction, LocationDetailFragment.OnInteraction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_frame, LocationResultFragment.newInstance(), null).commit();
        }
    }

    @Override
    public void onListItemClicked(LocationInfo item) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, LocationDetailFragment.newInstance(item), null).addToBackStack(null).commit();

    }

}
