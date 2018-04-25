package org.wit.mytwitterverfour.activties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.wit.mytwitterverfour.main.MyTweetApp;
import org.wit.mytwitterverfour.model.Tweet;

import olympus.mount.test.R;



/** Created by Bernie 21/12/17
 * This Class is a fragment and has a action bar
 *
 * Tweet Activity took guidance from :
 * https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-b/book-a-myrent-07%20(Fragments)/index.html#/04
 */



public class TweetActivity extends AppCompatActivity implements OnClickListener {
    private int target = 240;

    private EditText tweet_message;
    private TextView date;
    private Button tweetButton;
    private Tweet tweet;
    private CheckBox istweeted;
    private Button dateButton;
    private MyTweetApp app ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tweet);

       tweet_message= (EditText) findViewById(R.id.message);
      // tweet = new Tweet();
        tweet_message.addTextChangedListener((TextWatcher) this);
        tweetButton  = (Button)   findViewById(R.id.tweet);
        dateButton  = (Button)   findViewById(R.id.registration_date);
        dateButton.setEnabled(false);

       istweeted = (CheckBox) findViewById(R.id.istweeted);
        istweeted .setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
    }



    public void tweetButtonPressed(View view) {
        String text = tweet_message.getText().toString();

        if (text.length() > 0) {
            Tweet tweet = new Tweet(text, date.getText().toString());

        } else invalidInput(this);
    }



    private void invalidInput(TweetActivity tweetActivity) {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tweetButton: tweetButtonPressed(v);
        }
    }

    }




