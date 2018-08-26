package org.wit.mytwitterverfour.activties;

/**
 * Created by berni on 1/2/2018.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wit.mytwitterverfour.main.MyTweetApp;
import org.wit.mytwitterverfour.model.User;

import java.util.HashMap;
import java.util.Map;

import olympus.mount.test.R;


/**
 * Created by berni on 12/30/2017.
 * This class allow the user to register
 * Sign up done with guidance from :https://wit-ictskills-2017.github.io/mobile-app-dev/topic02-b/book-a-donation-03/index.html#/Donation-03
 */


public class Signup extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signupPressed(View view) {

        //setup sharedPreferences to save local data
        SharedPreferences.Editor editor = getSharedPreferences("users", MODE_PRIVATE).edit();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");
        TextView firstName = (TextView)  findViewById(R.id.firstName);
        TextView lastName  = (TextView)  findViewById(R.id.lastName);
        TextView email     = (TextView)  findViewById(R.id.Email);
        TextView password  = (TextView)  findViewById(R.id.Password);

        User user = new User (firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString());
        Map<String, User> users = new HashMap<>();
        //param1: key, param2: value
        final String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
//save the uuid as a key so we can lookup the user later.
        editor.putString(user.email, uuid);
        editor.apply();
        //save the uuid as a key so we can lookup the user later.



         myRef.child(uuid).setValue(user);

        MyTweetApp app = (MyTweetApp) getApplication();
        app.newUser(user);

        startActivity (new Intent(this, Welcome.class));
    }
}