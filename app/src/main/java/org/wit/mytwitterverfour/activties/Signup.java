package org.wit.mytwitterverfour.activties;

/**
 * Created by berni on 1/2/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wit.mytwitterverfour.model.User;

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");

        //get values from inputs
        TextView firstName = (TextView)  findViewById(R.id.firstName);
        TextView lastName  = (TextView)  findViewById(R.id.lastName);
        TextView email     = (TextView)  findViewById(R.id.Email);
        TextView password  = (TextView)  findViewById(R.id.Password);

        //create user object using values
     User user;
        user = new User(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString());

        //we use the push method to save data to firebase, when using push, firebase will automatically create a unique id for each entry.
        //this is how we save new data to an existing list without overwriting data.
        myRef.push().setValue(user);

        startActivity (new Intent(this, Welcome.class));
    }
}