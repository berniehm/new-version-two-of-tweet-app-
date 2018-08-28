package org.wit.mytwitterverfour.activties;

/**
 * Created by berni on 12/28/2017.
 * /**
 * Tweet Fragment took guidance from
 * https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-b/book-a-myrent-07%20(Fragments)/index.html#/04
 */

import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v4.app.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;



import org.wit.mytwitterverfour.main.MyTweetApp;
import org.wit.mytwitterverfour.model.Portfolio;
import org.wit.mytwitterverfour.model.Tweet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import olympus.mount.test.R;


import static org.wit.mytwitterverfour.helpers.ContactHelper.getContact;
import static org.wit.mytwitterverfour.helpers.ContactHelper.getEmail;
import static org.wit.mytwitterverfour.helpers.ContactHelper.sendEmail;


public class TweetFragment extends Fragment implements TextWatcher,
        OnCheckedChangeListener,
        OnClickListener,
        OnDateSetListener
{
    public static   final String  EXTRA_TWEET_ID = "myTweet.TWEET_ID";
    private static  final int     REQUEST_CONTACT = 1;

    private EditText message;
    private CheckBox tweeted;
    private Button   dateButton;
    private Button   tweeterButton;
    private Button   reportButton;
    private  String lettercount;

    private Tweet tweet;
    private Portfolio portfolio;

    private String emailAddress = "";
    // This field is initialized in `onActivityResult`.
    private Intent data;
    MyTweetApp app;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Long resId = (Long) getActivity().getIntent().getSerializableExtra(EXTRA_TWEET_ID);

        app = MyTweetApp.getApp();
        portfolio = app.portfolio;
        tweet = portfolio.getTweet(resId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        super.onCreateView(inflater,  parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweet, parent, false);

        TweetActivity tweetActivity = (TweetActivity)getActivity();


        addListeners(v);
        updateControls(tweet);

        return v;
    }

    private String getAddressFromLocation( Location location ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String strAddress = "";
        Address address;
        try {
            address = geocoder
                    .getFromLocation( location.getLatitude(), location.getLongitude(), 1 )
                    .get( 0 );
            strAddress = address.getAddressLine(0) +
                    " " + address.getAddressLine(1) +
                    " " + address.getAddressLine(2);
        }
        catch (IOException e ) {
        }

        return strAddress;
    }


    private void addListeners(View v)
    {
        message  = (EditText) v.findViewById(R.id.message);
        dateButton   = (Button)   v.findViewById(R.id.registration_date);
        tweeted       = (CheckBox) v.findViewById(R.id.istweeted);
        tweeterButton = (Button)   v.findViewById(R.id.tweet);
        reportButton = (Button)   v.findViewById(R.id.tweet_reportButton);


        message .addTextChangedListener(this);
        dateButton  .setOnClickListener(this);
        tweeted      .setOnCheckedChangeListener(this);
        tweeterButton.setOnClickListener(this);
        reportButton.setOnClickListener(this);
    }

    public void updateControls(Tweet tweet)
    {
        message.setText(tweet.message);
        tweeted.setChecked(tweet.tweeted);
        dateButton.setText(tweet.getDateString());
        tweeterButton.setText("Tweeter: "+tweet.tweeter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home: navigateUp(getActivity());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateUp(FragmentActivity activity) {
    }

    @Override
    public void onPause()
    {
        super.onPause();
        portfolio.saveTweets();
    }

    /**https://wit-ictskills-2017.github.io/mobile-app-dev/topic05-a/book-b-permissions/index.html#/01
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }

        switch (requestCode)
        {
            case REQUEST_CONTACT:
                this.data = data;
                checkContactsReadPermission();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {}

    @Override
    public void afterTextChanged(Editable c)
    {
        Log.i(this.getClass().getSimpleName(), "message " + c.toString());
        tweet.message = c.toString();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        tweet.tweeted = isChecked;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.registration_date      : Calendar c = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog (getActivity(), this,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                break;
            case R.id.tweet :
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
                tweeterButton.setText("Tweeter: "+tweet.tweeter);
                break;
            case R.id.tweet_reportButton :
                sendEmail(getActivity(), emailAddress, getString(R.string.tweet_report_subject), tweet.getTweetReport(getActivity()));
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        Date date = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
        tweet.date = date.getTime();
        dateButton.setText(tweet.getDateString());
    }

    //https://developer.android.com/training/permissions/requesting.html
    private void checkContactsReadPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //We can request the permission.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
        }
        else {
            //We already have permission, so go head and read the contact
            readContact();
        }
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    private void readContact() {
        String name = getContact(getActivity(), data);
        emailAddress = getEmail(getActivity(), data);
        tweet.tweeter = name;
        tweeterButton.setText("Tweeter: "+tweet.tweeter);
    }



    /**public void tweetPressed(View view) {

     if (lettercount = "";
     (message.getText().toString(), message.getText().toString())){
     startActivity (new Intent(this, TweetFragment.class));
     }
     else
     {
     Toast toast = Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT);
     toast.show();

     }*/









    //https://developer.android.com/training/permissions/requesting.html
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    readContact();
                }
            }
        }
    }
}
