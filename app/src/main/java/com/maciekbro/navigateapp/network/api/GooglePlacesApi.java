package com.maciekbro.navigateapp.network.api;

import com.maciekbro.navigateapp.model.json.GooglePlacesResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by RENT on 2017-04-10.
 */

public interface GooglePlacesApi {

    @GET("api/place/{searchType}/json")
    Observable<GooglePlacesResponse> getGoolePlaces(@Path("searchType") String searchType,
                                                    @QueryMap Map<String, String> mapOfParams);     //dobrze tak robic jak jest duzo parametrów get.


}
