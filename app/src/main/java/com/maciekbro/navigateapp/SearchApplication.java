package com.maciekbro.navigateapp;

import android.app.Application;

import com.maciekbro.navigateapp.dagger.DaggerSearchComponent;
import com.maciekbro.navigateapp.dagger.MainAppModule;
import com.maciekbro.navigateapp.dagger.SearchComponent;

/**
 *
 */

public class SearchApplication extends Application {


    private SearchComponent searchComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        searchComponent = DaggerSearchComponent.builder()   //dagger sam dodaje słowo Dagger do wykorzystywanych przez niego zmiennych
                .mainAppModule(new MainAppModule(this))
                .build();
    }


    public SearchComponent getSearchComponent() {
        return searchComponent;
    }
}
