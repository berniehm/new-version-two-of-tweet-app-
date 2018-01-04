package org.wit.mytwitterverfour.activties;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import olympus.mount.test.R;

/**
 * Created by berni on 1/2/2018.
 */

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void signupPressed(View view) {
        startActivity(new Intent(this, Signup.class));
    }
    public void loginPressed (View view)
    {
        startActivity (new Intent(this, Login.class));
    }
}
