package com.example.nitin.jgi.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.nitin.jgi.R;

public class BusTrackerMain extends AppCompatActivity {
    Switch swit;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_tracker_main);
        swit = findViewById(R.id.driver_switch);
        swit.setOnClickListener(listener);
        button = findViewById(R.id.enter);
        button.setOnClickListener(listener);
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.driver_switch:
                    if(swit.isChecked())
                        button.setText("Track Your Bus");
                    else
                        button.setText("Start");
                    break;
                case R.id.enter:
                        if (button.getText()=="Track Your Bus") {
                            Intent i = new Intent(BusTrackerMain.this, StudentTrack.class);
                            startActivity(i);
                        }
                        else
                        {
                            Intent j = new Intent(BusTrackerMain.this,DriverLogin.class);
                            startActivity(j);
                        }
                    break;
                default:break;
            }
        }
    };
}
