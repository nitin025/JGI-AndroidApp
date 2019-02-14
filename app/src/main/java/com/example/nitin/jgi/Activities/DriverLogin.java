package com.example.nitin.jgi.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nitin.jgi.App;
import com.example.nitin.jgi.DriverPanel;
import com.example.nitin.jgi.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class DriverLogin extends AppCompatActivity {
    Button bt;
    EditText username;
    EditText password;
    Editable user;
    Editable pass;
    App a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        bt = findViewById(R.id.button_login);
        username = findViewById(R.id.user_edit);
        password = findViewById(R.id.user_pw_edit);
        user = username.getText();
        pass = password.getText();


        bt.setOnClickListener(listen);
    }

    private View.OnClickListener listen = new View.OnClickListener()

    {


        @Override
        public void onClick(View v) {
            if (readObject(user.toString(), pass.toString())) {
                Intent i = new Intent(DriverLogin.this, DriverPanel.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
            }


        }
    };

    public boolean readObject(String user, String pass) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("DriverLogin");
        query.whereEqualTo("username", user);
        query.whereEqualTo("password", pass);

        try {
            List<ParseObject> value = query.find();
            if (value.size() == 1)
                return true;
            else
                return false;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}

