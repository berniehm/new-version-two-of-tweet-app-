package org.wit.mytwitterverfour.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by berni on 1/2/2018.
 * This class stores Json and aids Persitance
 */
public class PortfolioSerializer
{
    private Context mContext;
    private String mFilename;

    public PortfolioSerializer(Context c, String f)
    {
        mContext = c;
        mFilename = f;
    }

    public void saveTweets(ArrayList<Tweet> tweets) throws JSONException, IOException
    {
        // build an array in JSON
        JSONArray array = new JSONArray();
        for (Tweet c : tweets)
            array.put(c.toJSON());

        // write the file to disk
        Writer writer = null;
        try
        {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }
        finally
        {
            if (writer != null)
                writer.close();
        }
    }

    public ArrayList<Tweet> loadTweets() throws IOException, JSONException
    {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        BufferedReader reader = null;
        try
        {
            // open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of tweets from JSONObjects
            for (int i = 0; i < array.length(); i++)
            {
                tweets.add(new Tweet(array.getJSONObject(i)));
            }
        }
        catch (FileNotFoundException e)
        {
            // we will ignore this one, since it happens when we start fresh
        }
        finally
        {
            if (reader != null)
                reader.close();
        }
        return tweets;
    }
}