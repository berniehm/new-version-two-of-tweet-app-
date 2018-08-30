
package org.wit.mytwitterverfour.activties;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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



public class TweetActivity extends AppCompatActivity  {
    private int target = 240;

    private TextView tweetBody;
    private TextView date;
    private Button tweetButton;
    private Button selectContact;
    private EditText tweet;
    ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    public List<User> users    = new ArrayList<User>();
    public List text;

    private static final int REQUEST_CONTACT = 1;
    private Button   sendTweet;
    private MyTweetApp app;
    private String    emailAddress = "";
    private String    emailRetweet = "";
    private ArrayList<String >arrayList;
    final private CustomAdapter adapter = new CustomAdapter();

    public TweetActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tweet);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(getString(R.string.tweetsFirebaseNode));

        //setup firebase data listener for new tweets
        ///add an event listener to the query so we can get the result of it.

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            //this method will run when the data is changed. Since we used query.addListenerForSingleValueEvent, it will only be run once.
            //if we used query.addListenerForValueEvent, this method would run when we attach and run again anytime the data is changed (see firebase documentation).
            public void onDataChange(DataSnapshot dataSnapshot) {
                //deserialize data from firebase into class objects
                tweets = new ArrayList<Tweet>(); //clear array to save new tweets
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Tweet thisTweet = ds.getValue(Tweet.class);
                    tweets.add(thisTweet);
                }

                //refresh tweet list
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

        ListView listView = (ListView)findViewById(R.id.tweetList);
        listView.setAdapter(adapter);

        //get tweet button from view
        tweetButton = (Button) findViewById(R.id.tweet);

        tweetButton.setOnClickListener(new View.OnClickListener()



        {

            @Override
            public void onClick(View v) {
                tweet = (EditText) findViewById(R.id.message);
                String text = tweet.getText().toString();

                if (text.length() > 0) {
                    Tweet newTweet = new Tweet();
                    newTweet.setMessage(text); //set the tweet message
                    newTweet.setAmount(text.length()); //set the amount of characters

                    //get the logged in user name or id. if the values dont exist in the session it will be null
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String tweeter = prefs.getString(getString(R.string.sessionDisplayName),null);
                    newTweet.setTweeter(tweeter);

                    tweets.add(newTweet);

                    //get firebase database instance and tweet node reference
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(getString(R.string.tweetsFirebaseNode));

                    myRef.push().setValue(newTweet); //save tweet to firebase

                    tweet.setText(""); //clear the textbox when the tweet is sent

                    //adapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Tweet Posted",
                            Toast.LENGTH_LONG).show();

                }
                else { Toast.makeText(getApplicationContext(), "nothing tweeted",
                        Toast.LENGTH_LONG).show();

                }}
        });




    }

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return tweets.size();
        }

        @Override
        public Object getItem(int i)
        {
            return tweets.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup)
        {
            view = getLayoutInflater().inflate(R.layout.tweetitem,null);


            TextView tweet = (TextView) view.findViewById(R.id.itemName); //view for tweet
            TextView tweeter = (TextView) view.findViewById(R.id.username); //view for tweeter
            TextView count = (TextView) view.findViewById(R.id.charactercount); //view for character count

            Button delbtn = (Button) view.findViewById(R.id.delbtn);
            //set on click listener for the delete button
            delbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    Tweet tweetToDelete = tweets.get(i);

                    //query firebase to find the tweet to delete by its id.
                    Query q = FirebaseDatabase.getInstance().getReference(getString(R.string.tweetsFirebaseNode))
                            .orderByChild("id")
                            .equalTo(tweetToDelete.id);

                    //ad an event listener to get the data
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //iterate over the returned data from our query and delete the tweet from firebase
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                ds.getRef().removeValue(); //actual delete
                            }

                            Toast.makeText(getApplicationContext(), "Tweet Deleted",
                                    Toast.LENGTH_LONG).show(); //notify the user that the tweet was deleted
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //
                        }
                    });

                }
            });

            Tweet current = tweets.get(i);

            tweet.setText(current.getMessage());
            tweeter.setText(current.getTweeter());
            count.setText(Integer.toString(current.getAmount()));


            return view;

        }
    }

}





