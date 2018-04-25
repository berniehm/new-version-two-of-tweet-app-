package org.wit.mytwitterverfour.main;

/**
 * Created by berni on 1/3/2018.
 */


import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import org.wit.mytwitterverfour.model.Portfolio;
import org.wit.mytwitterverfour.model.Tweet;
import org.wit.mytwitterverfour.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by berni on 12/27/2017.
 * It is the main App Activity
 * this is created to hold an array list of tweets
 * also an array list of users
 */

public class MyTweetApp extends Application{

    private static MyTweetApp app;
    public final int target = 10000;
    public int totalTweeted = 0;
    public List<Tweet> tweets = new ArrayList<Tweet>();
    public List<User>     users     = new ArrayList<User>();
    public Portfolio portfolio;

    public static MyTweetApp getApp() {
        return app;
    }

    public boolean newDonation(Tweet tweet) {
        boolean targetAchieved = totalTweeted > target;

        if(!targetAchieved) {
            tweets.add(tweet);
            totalTweeted += tweet.amount;
        }
        else {
            Toast toast = Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT);
            toast.show();
        }
        return targetAchieved;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Tweet","Tweet App Started");
    }

    public void newUser(User user)
    {
        users.add(user);
    }

    public boolean validUser (String email, String password)
    {
        for (User user : users)
        {
            if (user.email.equals(email) && user.password.equals(password))
            {
                return true;
            }
        }
        return false;
    }

    public void newTweet(Tweet tweet) {
    }
}
