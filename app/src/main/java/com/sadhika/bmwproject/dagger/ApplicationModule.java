package com.sadhika.bmwproject.dagger;

import android.app.Application;
import android.content.Context;

import com.sadhika.bmwproject.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sadhika on 7/2/17.
 */

@Module
public class ApplicationModule {

    private Context mContext;
    Application application;

    public ApplicationModule(Application app, Context ctx)
    {
        application = app;
        mContext = ctx;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return mContext;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return application;
    }
}

