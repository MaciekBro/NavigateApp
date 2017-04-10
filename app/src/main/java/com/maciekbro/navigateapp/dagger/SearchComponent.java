package com.maciekbro.navigateapp.dagger;

import com.maciekbro.navigateapp.SearchApplication;
import com.maciekbro.navigateapp.network.GoogleRetrofitProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 */

@Singleton  //singleton jest scopem
@Component(modules = {MainAppModule.class,InterfaceModule.class})
public interface SearchComponent {

    //zwraca na zewnątrz GoogleRetrofitProvider tak zeby np. MapsActivityComponent je widzialo
    GoogleRetrofitProvider provideGoogleRetrofitProvider();

    SearchApplication provideSearchApplication();
}
