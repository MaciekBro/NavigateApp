package com.maciekbro.navigateapp.map.dagger;

import com.maciekbro.navigateapp.R;
import com.maciekbro.navigateapp.SearchApplication;
import com.maciekbro.navigateapp.dagger.scopes.ActivityScope;
import com.maciekbro.navigateapp.map.mvp.MapsMVP;
import com.maciekbro.navigateapp.map.mvp.MapsModel;
import com.maciekbro.navigateapp.network.GoogleRetrofitProvider;
import com.maciekbro.navigateapp.network.api.GooglePlacesApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by RENT on 2017-04-10.
 */

@Module
public class MapsActivityModule {

    @ActivityScope
    @Provides
        //bo w komponencie od ktorego jest zalezny ten modul to jest zwracane na zewnątrz
    MapsMVP.Model providesModel(GoogleRetrofitProvider googleRetrofitProvider, SearchApplication searchApplication) {

        Retrofit retrofit = googleRetrofitProvider.provideRetrofit();
        GooglePlacesApi api = retrofit.create(GooglePlacesApi.class);
        String apiKey = searchApplication.getString(R.string.google_places_key);

        return new MapsModel(api, apiKey);
    }
}
