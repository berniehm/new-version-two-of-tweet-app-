package org.wit.mytwitterverfour.activties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.wit.mytwitterverfour.main.MyTweetApp;
import org.wit.mytwitterverfour.model.Tweet;
import org.wit.mytwitterverfour.model.User;

import java.util.ArrayList;
import java.util.List;

import olympus.mount.test.R;



/** Created by Bernie 21/12/17
 * This Class is a fragment and has a action bar
 *
 * Tweet Activity took guidance from :
 * https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-b/book-a-myrent-07%20(Fragments)/index.html#/04
 */



public class TweetActivity extends AppCompatActivity implements View.onClickListner {
    private int target = 240;

    private TextView tweetBody;
    private Tweet tweet;
    private TextView date;
    private Button email;
    private Button selectContact;
    public List <Tweet> tweets    = new ArrayList<Tweet>();
    public List<User> users    = new ArrayList<User>();

    private static final int REQUEST_CONTACT = 1;
    private Button   sendTweet;
    private MyTweetApp app;
    private String    emailAddress = "";
    private String    emailRetweet = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = (MyTweetApp) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tweet);
        getSupportActionBar() public void setDisplayHomeAsUpEnabled setDisplayHomeAsUpEnabled(true);

        email = (Button) findViewById(R.id.make_tweet);
        email.setOnClickListner(this);


        public void tweetButtonPressed (View view){
            String text = tweet_message.getText().toString();

            if (text.length() > 0) {
                Tweet tweet = new Tweet(text, date.getText().toString());

            } else invalidInput(this);
        }


        private void invalidInput (TweetActivity tweetActivity){
        }


        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.tweetButton:
                    tweetButtonPressed(v);
            }
        }


        ( boolean displayHomeAsUpEnabled){
            this.displayHomeAsUpEnabled = displayHomeAsUpEnabled;
        }
    }

    private void setDisplayHomeAsUpEnabled(boolean b) {
    }




