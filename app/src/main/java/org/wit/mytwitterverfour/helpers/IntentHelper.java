package org.wit.mytwitterverfour.helpers;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;

import java.io.Serializable;

/**
 * Created by berni on 12/27/2017.
 * helpers class
 * https://wit-ictskills-2017.github.io/mobile-app-dev/topic03-b/book-a-myrent-02%20(Listview)/index.html#/06
 */

public class IntentHelper
{
    public static void startActivity (Activity parent, Class classname)
    {
        Intent intent = new Intent(parent, classname);
        parent.startActivity(intent);
    }

    public static void startActivityWithData (Activity parent, Class classname, String extraID, Serializable extraData)
    {
        Intent intent = new Intent(parent, classname);
        intent.putExtra(extraID, extraData);
        parent.startActivity(intent);
    }

    public static void startActivityWithDataForResult (Activity parent, Class classname, String extraID, Serializable extraData, int idForResult)
    {
        Intent intent = new Intent(parent, classname);
        intent.putExtra(extraID, extraData);
        parent.startActivityForResult(intent, idForResult);
    }
    public static void selectContact(Activity parent, int id)
    {
        Intent selectContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        parent.startActivityForResult(selectContactIntent, id);
    }
}

