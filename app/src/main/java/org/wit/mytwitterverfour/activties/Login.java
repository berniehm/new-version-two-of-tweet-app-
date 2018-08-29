package org.wit.mytwitterverfour.activties;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.wit.mytwitterverfour.main.MyTweetApp;
import org.wit.mytwitterverfour.model.User;

import java.util.ArrayList;

import olympus.mount.test.R;

/**
 * Created by berni on 12/30/2017.
 * This Class allows the user to Login by using an email and password
 *
 * Login Activity  done with guidance from:
 * https://wit-ictskills-2017.github.io/mobile-app-dev/topic02-b/book-a-donation-03/index.html#/Donation-03
 */


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
    }
    public void loginPressed (View view)
    {
        final MyTweetApp app = (MyTweetApp) getApplication();

        final TextView email     = (TextView)  findViewById(R.id.Email);
        final TextView password  = (TextView)  findViewById(R.id.Password);

        //check if the email input has text
        if ( !email.getText().toString().isEmpty() ) {

            //query firebase to find the user with the current email address entered.
            Query query = FirebaseDatabase.getInstance().getReference(getString(R.string.userFirebaseNode))
                    .orderByChild("email")
                    .equalTo(email.getText().toString())
                    .limitToFirst(1);

            ///add an event listener to the query so we can get the result of it.
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                //this method will run when the data is changed. Since we used query.addListenerForSingleValueEvent, it will only be run once.
                //if we used query.addListenerForValueEvent, this method would run when we attach and run again anytime the data is changed (see firebase documentation).
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //deserialize data from firebase into class objects
                    ArrayList<User> users = new ArrayList<User>();
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        User thisUser = ds.getValue(User.class);
                        users.add(thisUser);
                    }

                    //get the first user from the query, we should only have one anyway since we used .limitToFirst(1) in the query.
                    User user = users.get(0);

                    //compare passwords
                    if(password.getText().toString().equals(user.password))
                    {
                        //the user is logged in, lets save the user info so we have it when this user creates tweets.
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); //open application shared preferences
                        prefs.edit().putString(getString(R.string.sessionUsername),user.email).commit(); //save username
                        prefs.edit().putString(getString(R.string.sessionDisplayName), user.firstName + " " + user.lastName).commit(); //save display name

                        //start tweet activity
                        startActivity (new Intent(getApplicationContext(), TweetActivity.class));
                    }
                    else
                    {
                        //show the user a message that tells them their username or password is invalid
                        Toast toast = Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}

            });

        }
        //there was no text for the email TextView so show a message to the user.
        else
        {
            // show a Toast here to the user.
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter your email address", Toast.LENGTH_SHORT);
            toast.show();
        }



    }
}
