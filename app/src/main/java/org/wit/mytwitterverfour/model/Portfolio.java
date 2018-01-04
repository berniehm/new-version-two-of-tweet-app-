package org.wit.mytwitterverfour.model;

import android.util.Log;

import java.util.ArrayList;

import static org.wit.mytwitterverfour.helpers.LogHelpers.info;


/**
 * Created by berni on 1/2/2018.
 */


public class Portfolio
{
    public ArrayList<Tweet> tweets;
    private PortfolioSerializer   serializer;

    public Portfolio(PortfolioSerializer serializer)
    {
        this.serializer = serializer;
        try
        {
            tweets = serializer.loadTweets();
        }
        catch (Exception e)
        {
            info(this, "Error loading residences: " + e.getMessage());
            tweets = new ArrayList<Tweet>();
        }
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public Tweet getTweet(Long id) {
        Log.i(this.getClass().getSimpleName(), "Long parameter id: " + id);

        for (Tweet res : tweets) {
            if (id.equals(res.id)) {
                return res;
            }
        }
        return null;
    }

    public void deleteTweet(Tweet tweet)
    {
        tweet.remove(tweet);
        saveTweets();
    }


    public boolean saveTweets()
    {
        try
        {
            serializer.saveTweets(tweets);
            info(this, "Tweets saved to file");
            return true;
        }
        catch (Exception e)
        {
            info(this, "Error saving tweets: " + e.getMessage());
            return false;
        }

    }

}