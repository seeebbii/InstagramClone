package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("QQY5J3iKJ19glyGSvxZNHUC5NH6X2vWzxVFEqoK9")
                // if defined
                .clientKey("NZWyf5aLHIRqXejIMHJG5wLc0Nd3YVUdRnjYNUJB")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
