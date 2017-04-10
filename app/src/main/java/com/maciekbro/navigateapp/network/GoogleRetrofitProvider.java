package com.maciekbro.navigateapp.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maciekbro.navigateapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RENT on 2017-04-10.
 */

public class GoogleRetrofitProvider {

    private String baseUrl;

    public GoogleRetrofitProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Retrofit provideRetrofit(){  //zwracany jest klient retrofita
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient;

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Logger myLogger = message -> Log.e("SEARCH_AND_NAVIGATE", message);
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(myLogger);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = httpClientBuilder
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
        } else {
            okHttpClient = httpClientBuilder
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();

        }

        Gson gson1 = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()  //wezmie tylko te pola z anotacją @expose
                .setDateFormat("yyyy-MM-dd HH:mm:ssZ").create();    //jezeli json bedzie mial date to automatycznie to wykryje i zamieni na nasz format

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson1))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //mowimy retrofitowi ze ma wspierać exJave
                .client(okHttpClient)
                .build();
    }
}
