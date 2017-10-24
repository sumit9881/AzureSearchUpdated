package com.sadhika.bmwproject.dagger;


import com.sadhika.bmwproject.LocationResultPresenterUnitTest;
import com.sadhika.bmwproject.presenter.LocationResultPresenter;

import dagger.Component;

/**
 * Created by Sadhika on 7/17/17.
 */

@Component
public interface TestNetComponent extends NetComponent {
    void inject(LocationResultPresenterUnitTest unitTest);

}
