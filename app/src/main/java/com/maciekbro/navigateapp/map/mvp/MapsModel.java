package com.maciekbro.navigateapp.map.mvp;

import com.maciekbro.navigateapp.model.dto.PlacesDto;
import com.maciekbro.navigateapp.model.dto.SearchParamsDto;
import com.maciekbro.navigateapp.model.json.GooglePlacesResponse;
import com.maciekbro.navigateapp.model.json.Result;
import com.maciekbro.navigateapp.network.api.GooglePlacesApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by RENT on 2017-04-10.
 */

public class MapsModel implements MapsMVP.Model {

    public static final String KEY = "key";
    private GooglePlacesApi googlePlacesApi;
    private String apiKey;
    private Map<String, String> querys;

    public MapsModel(GooglePlacesApi googlePlacesApi, String apiKey) {
        this.googlePlacesApi = googlePlacesApi;
        this.apiKey = apiKey;
    }

    //zamiana otrzymanego jsona na obiekt klasy GooglePlacesResponse!

    @Override
    public Observable<List<PlacesDto>> getPlacesDto(SearchParamsDto searchParamsDto) {

        querys = searchParamsDto.getQuerys();
        //dodaliśmy wartość klucza do zapytania, bo jest wymagane przez API!!!!
        querys.put(KEY,apiKey);

        return googlePlacesApi.getGoolePlaces(searchParamsDto.getSearchType(), querys)
                .map(new Function<GooglePlacesResponse, List<PlacesDto>>() {
            @Override
            public List<PlacesDto> apply(GooglePlacesResponse googlePlacesResponse) throws Exception {
                List<Result> results = googlePlacesResponse.getResults();
                List<PlacesDto> placesDtos = new ArrayList<>();
                for (Result result : results) {
                    placesDtos.add(new PlacesDto(result));
                }
                return placesDtos;
            }
        });
    }
}
