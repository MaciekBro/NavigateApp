package com.maciekbro.navigateapp.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.maciekbro.navigateapp.SearchApplication;
import com.maciekbro.navigateapp.network.GoogleRetrofitProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 */


@Module
public class MainAppModule {

    public static final String SHARED_PREFS_NAME = "default";
    SearchApplication application;

    public MainAppModule(SearchApplication application) {
        this.application = application;
    }

    /**
     *Dostarczamy context aplikacji wszystkim którzy jej potrzebują
     */
    @Provides
    @Singleton
    SearchApplication provideContext(){
        return application;
    }
    /**
     *Dostarczamy instancje shared preferensces wszystkim którzy jej potrzebują
     */
    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(){
        return this.application.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    GoogleRetrofitProvider provideGoogleRetrofitProvider() {    //tworzy retrofita z baseUrl jak poniżej!
        return new GoogleRetrofitProvider("https://maps.googleapis.com/maps/");
    }

}
