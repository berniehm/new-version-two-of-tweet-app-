package org.wit.mytwitterverfour.helpers;

/**
 * Created by berni on 1/2/2018.
 * https://wit-ictskills-2017.github.io/mobile-app-dev/topic03-b/book-a-myrent-02%20(Listview)/index.html#/06
 */

import android.util.Log;


public class LogHelpers {
    public static void info(Object parent, String message)
    {
        Log.i(parent.getClass().getSimpleName(), message);
    }
}

