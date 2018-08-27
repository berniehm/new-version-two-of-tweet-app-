package org.wit.mytwitterverfour.model;

/**
 * Created by berni on 21/12/2017.
 * This class stores users details
 * Done with guidance from:
 * https://wit-ictskills-2017.github.io/mobile-app-dev/topic02-b/book-a-donation-03/index.html#/Donation-03
 */
public class User {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public User(String id ,String firstName, String lastName, String email, String password) {
        this.id = id ;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User() {
        /** this is here because firebase needs a class with out parameters\**
         *
         *
         */
    }
}



