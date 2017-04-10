package com.maciekbro.navigateapp.map.mvp;

import com.maciekbro.navigateapp.model.dto.PlacesDto;
import com.maciekbro.navigateapp.model.dto.SearchParamsDto;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by RENT on 2017-04-10.
 */

public interface MapsMVP {   //zeby bylo wiadomo ze to jest prezenter do map

    interface Model{        //zwraca dane, robi logikę biznesową!

        Observable<List<PlacesDto>> getPlacesDto(SearchParamsDto searchParamsDto); // Dto - klasa danowa wewnątrz aplikacji
    }

    interface Presenter{     //jest łącznikiem miedzy modelem a widokiem

//        void search(String searchType, Map<String,String> querys);
        void search(SearchParamsDto searchParamsDto);   //jak cos sie zmieni to zmienimy tylko klasę!
        void clearSubscriptions();
    }
    interface View{
        void showPlaces(List<PlacesDto> placesDtos);
    }

}
