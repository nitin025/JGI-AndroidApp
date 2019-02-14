package com.example.nitin.jgi.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitin.jgi.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.List;

public class StudentTrack extends FragmentActivity implements OnMapReadyCallback,
        View.OnClickListener {

    private GoogleMap mMap;
    Spinner staticSpinner;
    String route;
    ParseGeoPoint driverLocation;
    ArrayAdapter<CharSequence> staticAdapter;
    LocationManager mLocationManager;
    LocationListener locationListener;
    ParseGeoPoint riderLocation;
    TextView distance;


    Marker marker1,marker2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_track);
        distance = findViewById(R.id.tracker_text_view);

        //Spinner Values (Routes) Declaration
        staticSpinner = findViewById(R.id.rider_spinner);
        staticAdapter = ArrayAdapter.createFromResource(this, R.array.route_array, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);
        staticSpinner.setOnItemSelectedListener(listen_spinner);
        route = (String)staticSpinner.getSelectedItem();
        driverLocation = new ParseGeoPoint(0,0);
        riderLocation = new ParseGeoPoint(0,0);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       // updateLocation();




    }
     private Spinner.OnItemSelectedListener listen_spinner = new Spinner.OnItemSelectedListener(){

         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             route = (String) staticSpinner.getSelectedItem();
             //Toast.makeText(getApplicationContext(),route+" route selected",Toast.LENGTH_SHORT).show();
             //final ParseGeoPoint riderLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
             updateLocation();




         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     } ;



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-34,151), 11));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(-34,151)).title("You"));

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationManager.removeUpdates(locationListener);
    }
    public void updateDriverLocation(final String rout)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DriverLocation");
        query.whereEqualTo("Route", rout);
        try {
            List<ParseObject> value = query.find();
            ParseObject val = value.get(0);
            String did = val.getObjectId();
            query.getInBackground(did, new GetCallback<ParseObject>() {
                public void done(ParseObject entity, ParseException e) {
                    if (e == null) {
                        // Update the fields we want to
                        driverLocation = new ParseGeoPoint(entity.getParseGeoPoint("Location").getLatitude(),entity.getParseGeoPoint("Location").getLongitude());
                        //Toast.makeText(getApplicationContext(),"In Sub Method",Toast.LENGTH_SHORT).show();
                        LatLng latLng1 = new LatLng(driverLocation.getLatitude(),driverLocation.getLongitude());
                        if (marker2 != null)
                        {
                            marker2.remove();
                            marker2 = mMap.addMarker(new MarkerOptions().position(latLng1).title(rout+" BUS").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1,15));
                        }
                        else {
                            marker2 = mMap.addMarker(new MarkerOptions().position(latLng1).title(rout+" BUS").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 21));
                        }
                        // All other fields will remain the same
                        entity.saveInBackground();

                    }
                }
            });

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Double distanceInKilo = driverLocation.distanceInKilometersTo(riderLocation);
        Double distanceOneDP = (double) Math.round(distanceInKilo * 10)/10;
        distance.setText("Your Bus is "+distanceOneDP+" Kilometres away");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLocation();


            }
        },5000);

    }
    public void updateLocation()
    {
        mMap.clear();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        }
       // Toast.makeText(getApplicationContext(),"In Main Method",Toast.LENGTH_SHORT).show();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                riderLocation = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
                if (marker1 != null)
                {
                    marker1.remove();
                    marker1 = mMap.addMarker(new MarkerOptions().position(latLng).title("YOU"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                }
                else {
                    marker1 = mMap.addMarker(new MarkerOptions().position(latLng).title("YOU"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21));
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
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        updateDriverLocation(route);
    }
}
