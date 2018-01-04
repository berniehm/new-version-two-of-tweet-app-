package org.wit.mytwitterverfour.activties;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import olympus.mount.test.R;


public class TweetActivity extends AppCompatActivity {
    ActionBar actionBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tweet);
    }
}