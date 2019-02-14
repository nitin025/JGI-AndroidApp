package com.example.nitin.jgi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class DriverPanel extends AppCompatActivity {
    Spinner staticSpinner;
    String route;
    Button start_tracker_activity;
    LocationManager locationManager;
    LocationListener locationListener;
    Button stop_tracker_activity;
    ArrayAdapter<CharSequence> staticAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_panel);
        staticSpinner = findViewById(R.id.route_spinner);
        staticAdapter = ArrayAdapter.createFromResource(this, R.array.route_array, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);
        staticSpinner.setOnItemSelectedListener(listen_the_spinner);

        start_tracker_activity = findViewById(R.id.start_tracker);
        stop_tracker_activity = findViewById(R.id.stop_tracker);
        start_tracker_activity.setOnClickListener(listen);
        stop_tracker_activity.setOnClickListener(listen);

    }

    private View.OnClickListener listen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start_tracker:
                    updateLocation();
                    break;
                case R.id.stop_tracker:
                    terminateConnection();
                    break;
                default:
                    break;
            }
        }
    };

    public void updateLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


        locationListener = new LocationListener() {
                @Override
            public void onLocationChanged(Location location) {
                final ParseGeoPoint riderLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("DriverLocation");
                    query.whereEqualTo("Route", route);
                    try {
                        List<ParseObject> value = query.find();
                        ParseObject val = value.get(0);
                        String id = val.getObjectId();
                        query.getInBackground(id, new GetCallback<ParseObject>() {
                            public void done(ParseObject entity, ParseException e) {
                                if (e == null) {
                                    // Update the fields we want to
                                    entity.put("Location", riderLocation);

                                    // All other fields will remain the same
                                    entity.saveInBackground();

                                }
                                }
                            });
                    }
                    catch (ParseException e) {
                        e.printStackTrace();
                    }


                }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,400,1,locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,400,1,locationListener);
    }
    public void terminateConnection()
    {
        locationManager.removeUpdates(locationListener);
    }
    private Spinner.OnItemSelectedListener listen_the_spinner = new Spinner.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            route = (String) staticSpinner.getSelectedItem();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}