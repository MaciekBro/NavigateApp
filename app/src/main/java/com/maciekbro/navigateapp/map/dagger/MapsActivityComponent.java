package com.maciekbro.navigateapp.map.dagger;

import com.maciekbro.navigateapp.dagger.SearchComponent;
import com.maciekbro.navigateapp.dagger.scopes.ActivityScope;
import com.maciekbro.navigateapp.map.MapsActivity;

import dagger.Component;

/**
 * Created by RENT on 2017-04-10.
 */

//musimy to podac daggerowi, przy injection wiadomo bedzie ze ma robic to samo dla jednego scope'a
//jesli component umrze, to garbageCollector nie bedzie trzymal referencji do zaleznych od niego rzeczy
@ActivityScope
//wszystkie rzeczy ktore wystawia na zewnątrz searchComponent bedzie widoczny dla tego interface (protected i public)!!

//MapsComponent "dziedziczy" po SearchComponencie, czyli moze wstrzykiwać to co wstrzykuje SearchComponent
//też moze wstrzykiwać to co daje mu MapsActivityModule
@Component(dependencies = SearchComponent.class, modules = MapsActivityModule.class)
//do wstrzykiwania czegos do map

public interface MapsActivityComponent {

    void inject(MapsActivity mapsActivity);

}
