package com.sadhika.bmwproject.dagger;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sadhika.bmwproject.model.retrofit.BMWService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sadhika on 7/2/17.
 */

@Module
public class NetModule {

    private String mUrl;

    public NetModule(String url) {
        mUrl = url;
    }

    @Provides
    @Singleton
    BMWService providesBMWApi(Retrofit retrofit) {
        return retrofit.create(BMWService.class);

    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory convertorFactory, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(mUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(convertorFactory)
                .build();
    }

    @Provides
    @Singleton
    GsonConverterFactory providesGsonConvertorFactor(Gson gson) {
        return  GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson proviesGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder().cache(cache).build();
    }

    @Provides
    @Singleton
    Cache providesCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }
}
