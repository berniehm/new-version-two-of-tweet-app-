package org.wit.mytwitterverfour.activties;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.wit.mytwitterverfour.main.MyTweetApp;

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
       MyTweetApp app = (MyTweetApp) getApplication();

        TextView email     = (TextView)  findViewById(R.id.Email);
        TextView password  = (TextView)  findViewById(R.id.Password);

        if (app.validUser(email.getText().toString(), password.getText().toString()))
        {
            startActivity (new Intent(this, TweetActivity.class));
        }
        else
        {
            Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
