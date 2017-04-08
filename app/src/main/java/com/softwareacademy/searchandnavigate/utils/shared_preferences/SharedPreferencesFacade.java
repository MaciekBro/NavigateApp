package com.softwareacademy.searchandnavigate.utils.shared_preferences;

/**
 *
 */

public interface SharedPreferencesFacade {

    void writeString(String value, String key);
    String getString(String key);

    void putObject(String key, Object any);
    <T> T getObject(String key, Class<T> object);
}
