package com.softwareacademy.searchandnavigate.utils;

import android.app.Activity;
import android.view.View;

/**
 *
 */

public class ViewsUtils {

    public static <T extends View> T findView(Activity activity,int id) {
        return (T) activity.findViewById(id);
    }

    public static <T extends View> T findView(View view,int id) {
        return (T) view.findViewById(id);
    }
}
