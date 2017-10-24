package com.sadhika.bmwproject;

import android.app.Application;

import com.sadhika.bmwproject.dagger.ApplicationModule;
import com.sadhika.bmwproject.dagger.DaggerNetComponent;
import com.sadhika.bmwproject.dagger.LocationModule;
import com.sadhika.bmwproject.dagger.NetComponent;
import com.sadhika.bmwproject.dagger.NetModule;


/**
 * Created by Sadhika on 7/2/17.
 */

public class App extends Application {

    private static final String URL = "http://localsearch.azurewebsites.net";
    private NetComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerNetComponent.builder().applicationModule(new ApplicationModule(this, this)).locationModule(new LocationModule(this)).netModule(new NetModule(URL)).build();

    }

    public NetComponent getComponent() {
        return mComponent;
    }

}
