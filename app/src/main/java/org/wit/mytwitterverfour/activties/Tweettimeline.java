package org.wit.mytwitterverfour.activties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.wit.mytwitterverfour.model.Tweet;

import java.util.ArrayList;

import olympus.mount.test.R;

public class Tweettimeline extends AppCompatActivity {
    ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    final private CustomAdapter adapter = new CustomAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tweettimeline);
        TextView header = (TextView) findViewById(R.id.userheader);
        String Useremail = getIntent().getStringExtra("USER_EMAIL");
        String Username= getIntent().getStringExtra("USER_Name");
        header.setText(Username + "'s Timeline");
        Query query = FirebaseDatabase.getInstance().getReference(getString(R.string.tweetsFirebaseNode))
                .orderByChild("tweeterEmail")
                .equalTo(Useremail);
        ListView lv = (ListView) findViewById(R.id.usertweets);
        lv.setAdapter(adapter);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            //this method will run when the data is changed. Since we used query.addListenerForSingleValueEvent, it will only be run once.
            //if we used query.addListenerForValueEvent, this method would run when we attach and run again anytime the data is changed (see firebase documentation).
            public void onDataChange(DataSnapshot dataSnapshot) {
                //deserialize data from firebase into class objects
                tweets = new ArrayList<Tweet>(); //clear array to save new tweets
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Tweet thisTweet = ds.getValue(Tweet.class);
                    tweets.add(thisTweet);
                }



                //refresh tweet list
                adapter.notifyDataSetChanged();

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

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

}}





