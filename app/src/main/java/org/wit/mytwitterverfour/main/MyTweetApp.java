package org.wit.mytwitterverfour.main;

/**
 * Created by berni on 1/3/2018.
 */


import android.app.Application;

import org.wit.mytwitterverfour.model.Portfolio;
import org.wit.mytwitterverfour.model.PortfolioSerializer;
import org.wit.mytwitterverfour.model.User;


import android.app.Application;


import java.util.ArrayList;
import java.util.List;

import static org.wit.mytwitterverfour.helpers.LogHelpers.info;


/**
 * Created by berni on 12/27/2017.
 * It is the main App Activity
 * this is created to hold an array list of tweets
 * also an array list of users
 */

public class MyTweetApp extends Application {
    protected static MyTweetApp app;
    private static final String FILENAME = "portfolio.json";

    public   Portfolio portfolio;
    public List<User> users = new ArrayList<User>();
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        portfolio = new Portfolio(serializer);
        app = this;
        info(this, "MyTweet app launched");
    }

    public static MyTweetApp getApp() {
        return app;
    }

    public void newUser(User user) {
        this.user = user;
        users.add(user);

    }

    public boolean validUser(String email, String password) {
        for (User user : users) {
            if (user.email.equals(email) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

}
