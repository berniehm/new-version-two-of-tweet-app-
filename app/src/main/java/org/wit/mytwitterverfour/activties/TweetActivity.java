package org.wit.mytwitterverfour.activties;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.mytwitterverfour.main.MyTweetApp;
import org.wit.mytwitterverfour.model.Tweet;
import org.wit.mytwitterverfour.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import olympus.mount.test.R;


/** Created by Bernie 21/12/17
 * This Class is a fragment and has a action bar
 *
 * Tweet Activity took guidance from :
 * https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-b/book-a-myrent-07%20(Fragments)/index.html#/04
 */



public class TweetActivity extends AppCompatActivity  {
    private int target = 240;

    private TextView tweetBody;
    private TextView date;
    private Button email;
    private Button selectContact;
    private EditText tweet;
    public List <Tweet> tweets    = new ArrayList<Tweet>();
    public List<User> users    = new ArrayList<User>();

    private static final int REQUEST_CONTACT = 1;
    private Button   sendTweet;
    private MyTweetApp app;
    private String    emailAddress = "";
    private String    emailRetweet = "";
private ArrayList<String >arrayList;
private ArrayAdapter<String>adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = (MyTweetApp) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tweet);
        ListView listView = (ListView)findViewById(R.id.tweetList);
        String items = ("Apple,Banana,Grape");
        arrayList = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this, R.layout.activity_tweet_list, R.id.txtitem,arrayList);
        listView.setAdapter(adapter);
        final EditText message = (EditText) findViewById(R.id.message);
        email = (Button) findViewById(R.id.tweet);

       ;
        email.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                ActionBar.Tab tweet_message;
                tweet = (EditText) findViewById(R.id.message);
                String text = tweet.getText().toString();
                String newItem = message.getText().toString();
                arrayList.add(newItem);
                adapter.notifyDataSetChanged();

                if (text.length() > 0) {
                   // Tweet tweet = new Tweet(text, date.getText().toString());
                    Toast.makeText(getApplicationContext(), text,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

}}




