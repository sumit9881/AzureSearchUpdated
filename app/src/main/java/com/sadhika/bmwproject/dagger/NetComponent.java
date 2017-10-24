package com.sadhika.bmwproject.dagger;

import com.sadhika.bmwproject.model.CurrentLocationProvider;
import com.sadhika.bmwproject.model.LocationInfoProvider;
import com.sadhika.bmwproject.presenter.LocationResultPresenter;
import com.sadhika.bmwproject.view.LocationResultFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sadhika on 7/2/17.
 */
@Singleton
@Component(modules = {NetModule.class, ApplicationModule.class, LocationModule.class})
public interface NetComponent {

    void inject(LocationResultPresenter fragment);
}
