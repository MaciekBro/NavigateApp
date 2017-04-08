package com.softwareacademy.searchandnavigate;

import android.app.Application;

import com.softwareacademy.searchandnavigate.dagger.DaggerSearchComponent;
import com.softwareacademy.searchandnavigate.dagger.MainAppModule;
import com.softwareacademy.searchandnavigate.dagger.SearchComponent;

/**
 *
 */

public class SearchApplication extends Application {


    private SearchComponent searchComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        searchComponent = DaggerSearchComponent.builder()
                .mainAppModule(new MainAppModule(this))
                .build();
    }


    public SearchComponent getSearchComponent() {
        return searchComponent;
    }
}
