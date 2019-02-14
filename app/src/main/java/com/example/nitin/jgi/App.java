package com.example.nitin.jgi;

import android.app.Application;
import android.util.Log;

import com.example.nitin.jgi.Activities.DriverLogin;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class App extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );




    }




    //This method is called to retrieve real time driver's location
    public void retrieveLocation()
    {

    }



    //This method is called update the driver's location.
    public void updateLocation()
    {

    }




    //This method is called to check whether there is a driver credential is correct or not
    public void loginAuthenticate()
    {

    }









    // TODO: 05-12-2018  Uncomment the below method only if initialization is required.

    //This Method is for One Time use only for creating objects in the cloud enable only if needed at your own risk
    /*
    public void createObject()
    {
        String array[] = {"akash","ramu" ,
                "sitaram" ,
                "arun" ,
                "kiran" ,
                "mahadev" ,
                "ramesh" ,
                "basavanna" ,
                "mama"};
        String pass[] = {"akash123",
                "ramu123" ,
                "sitaram123" ,
                "arun123" ,
                "kiran123" ,
                "mahadev123" ,
                "ramesh123" ,
                "basavanna123" ,
                "mama123"};
        for (int i =0;i<array.length;i++)
        {
            ParseObject myNewObject = new ParseObject("DriverLogin");
            myNewObject.put("username", array[i]);
            myNewObject.put("password",pass[i]);
            myNewObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    // Here you can handle errors, if thrown. Otherwise, "e" should be null
                }
            });

        }


    }*/
}
