package com.softwareacademy.searchandnavigate.utils.shared_preferences;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

/**
 *
 */

public class SharedPreferencesFacadeImpl implements SharedPreferencesFacade {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Inject
    public SharedPreferencesFacadeImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void writeString(String value, String key) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    @Override
    public void putObject(String key, Object any) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        writeString(key, gson.toJson(any));
    }

    @Override
    public <T> T getObject(String key, Class<T> object) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(sharedPreferences.getString(key, ""), object);
    }


}
