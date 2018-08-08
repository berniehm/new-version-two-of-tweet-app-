package org.wit.mytwitterverfour.activties;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.wit.mytwitterverfour.main.MyTweetApp;
import org.wit.mytwitterverfour.model.Portfolio;
import org.wit.mytwitterverfour.model.Tweet;

import java.util.ArrayList;

import olympus.mount.test.R;


/**
 * Created by berni on 12/22/2017.
 * edited h july 2018
 */

public class TweetListActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private Portfolio portfolio;
    private TweetAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_tweet_list);

        listView = (ListView) findViewById(R.id.tweetList);

        MyTweetApp app = (MyTweetApp) getApplication();
        portfolio = app.portfolio;


        adapter = new TweetAdapter(this, portfolio.tweets);
        listView.setAdapter(adapter);

    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Tweet tweet = adapter.getItem(position);
        Intent intent = new Intent(this, TweetActivity.class);
        //intent.putExtra("Tweet_ID", tweet.id);
        startActivity(intent);

    }
    @Override
    public void onResume()
    {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}


class TweetAdapter extends ArrayAdapter<Tweet>
{
    private Context context;

    public TweetAdapter(Context context, ArrayList<Tweet> tweets)
    {
        super(context, 0, tweets);
        this.context = context;
    }

    public TweetAdapter(TweetListActivity context, ArrayList<Tweet> tweets) {
        super( context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.activity_tweet_list, null);
        }
        Tweet text = getItem(position);

        //TextView message = (TextView) convertView.findViewById(R.id.tweets_list_item_message);
       //message.setText(text.message);

        //TextView dateTextView = (TextView) convertView.findViewById(R.id.tweets_list_item_dateTextView);
        //dateTextView.setText(text.getDateString());



        return convertView;
    }
}