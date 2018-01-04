package org.wit.mytwitterverfour.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Date;
import java.util.Random;

import olympus.mount.test.R;

/**
 * Created by berni on 1/2/2018.
 */

public class Tweet {
    public Long id;
    public Long date;


    public String message;
    public boolean tweeted;
    public String  tweeter;

    //JSON names for instance fields
    private static final String JSON_ID = "id";
    private static final String JSON_MESSAGE = "message";
    private static final String JSON_DATE = "date";
    private static final String JSON_TWEETED = "tweeted";
    private static final String JSON_TWEETER  = "tweeter";

    public Tweet() {
        id = unsignedLong();
        date = new Date().getTime();
        message = "";
        tweeter = ": none presently";
    }

    //Constructor for loading JSON objects
    public Tweet(JSONObject json) throws JSONException {
        id = json.getLong(JSON_ID);
        message = json.getString(JSON_MESSAGE);
        date = json.getLong(JSON_DATE);
        tweeted = json.getBoolean(JSON_TWEETED);
        tweeter = json.getString(JSON_TWEETER);
    }

    /**
     * Generate a long greater than zero
     *
     * @return Unsigned Long value greater than zero
     */
    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <= 0);
        return rndVal;
    }

    public void setMessage(String meggage) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getDateString() {
        return "Registered:" + dateString();
    }

    private String dateString() {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }

    //Saves model as JSON object
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, Long.toString(id));
        json.put(JSON_MESSAGE, message);
        json.put(JSON_DATE, date);
        json.put(JSON_TWEETED, tweeted);
        json.put(JSON_TWEETER, tweeter);
        return json;
    }
    public String getTweetReport(Context context) {
        String tweetedString = "";
        if (tweeted) {
            tweetedString = context.getString(R.string.tweet_report_tweeted);
        }
        else {
            tweetedString = context.getString(R.string.tweet_report_not_tweeted);
        }

        String prospectiveTweeter = tweeter;
        if (tweeter == null) {
            prospectiveTweeter = context.getString(R.string.tweet_report_nobody_interested);
        }
        else {
            prospectiveTweeter = context.getString(R.string.tweet_report_prospective_tweeter);
        }

        String report = "Location " + message + " Date: " + dateString() + " " + tweetedString + " " + prospectiveTweeter;
        return report;

    }


    public void remove(Tweet tweet) {
    }
}
