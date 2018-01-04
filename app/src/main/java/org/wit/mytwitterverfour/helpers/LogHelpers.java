package org.wit.mytwitterverfour.helpers;

/**
 * Created by berni on 1/2/2018.
 */

import android.util.Log;

/**
 * Created by berni on 12/27/2017.
 */

public class LogHelpers {
    public static void info(Object parent, String message)
    {
        Log.i(parent.getClass().getSimpleName(), message);
    }
}

