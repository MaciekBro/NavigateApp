package com.softwareacademy.searchandnavigate.utils;


import android.util.Log;

import com.softwareacademy.searchandnavigate.BuildConfig;


/**
 *
 */

public class AppLog {
    public static void log(String tag, String text) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, text);
        }
    }
}
