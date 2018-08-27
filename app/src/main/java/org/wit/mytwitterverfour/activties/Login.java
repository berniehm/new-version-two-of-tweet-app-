package org.wit.mytwitterverfour.activties;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.wit.mytwitterverfour.main.MyTweetApp;
import org.wit.mytwitterverfour.model.User;

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
    {startActivity (new Intent(getApplicationContext(), TweetActivity.class));
       final MyTweetApp app = (MyTweetApp) getApplication();

        final TextView email     = (TextView)  findViewById(R.id.Email);
        final TextView password  = (TextView)  findViewById(R.id.Password);
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       SharedPreferences prefs = getSharedPreferences("users", MODE_PRIVATE);
       // String UserID = prefs.getString( email.getText().toString(), null);
      // if (UserID != null) {
          String name = prefs.getString(email.getText().toString(), "No name defined");



        DatabaseReference readDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference readmyRef = readDatabase.child("users");

        readmyRef.child(name);
        readmyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Toast.makeText(getApplicationContext(), user.password, Toast.LENGTH_LONG).show();
                if (password.getText().toString().equals(user.password)) {
                    startActivity(new Intent(getApplicationContext(), TweetActivity.class));
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }



    }

