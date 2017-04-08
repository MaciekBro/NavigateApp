package com.maciekbro.navigateapp.utils;


import android.util.Log;

import com.maciekbro.navigateapp.BuildConfig;


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
