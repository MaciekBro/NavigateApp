package com.maciekbro.navigateapp.map.mvp;

import com.maciekbro.navigateapp.model.dto.PlacesDto;
import com.maciekbro.navigateapp.model.dto.SearchParamsDto;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by RENT on 2017-04-10.
 */

public class MapsPresenter implements MapsMVP.Presenter {

    private MapsMVP.View view;
    private MapsMVP.Model model;
    private Disposable disposable;

    public MapsPresenter(MapsMVP.View view, MapsMVP.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void search(SearchParamsDto searchParamsDto) {
        //  /\ wysylamy proces do innego wątku, wszystko co jest nad tym bedzie na wskazanym wątku!
//powrót na główny wątek
//placesDtos sa prekazywane przez searchParamsDto
//to jest nasz error
        disposable = model.getPlacesDto(searchParamsDto)
                .subscribeOn(Schedulers.computation())  //  /\ wysylamy proces do innego wątku, wszystko co jest nad tym bedzie na wskazanym wątku!
                .observeOn(AndroidSchedulers.mainThread()) //powrót na główny wątek
                .subscribe(new Consumer<List<PlacesDto>>() {
                    @Override
                    public void accept(List<PlacesDto> placesDtos) throws Exception {   //placesDtos sa prekazywane przez searchParamsDto
                        MapsPresenter.this.view.showPlaces(placesDtos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {  //to jest nasz error
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void clearSubscriptions() {
        //bronimy sie przed wyciekiem pamięci
        if (disposable != null) {
            disposable.dispose();
        }
    }


    //anonimowa klasa wewnętrzna!
//    private class MyClass implements Consumer<List<PlacesDto>> {
//
//        @Override
//        public void accept(List<PlacesDto> placesDtos) throws Exception {
//            MapsPresenter.this.view.showPlaces(placesDtos);
//        }
//    }

}
