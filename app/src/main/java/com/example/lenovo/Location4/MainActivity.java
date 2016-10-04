package com.example.lenovo.Location4;


import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.MapFragment;

import android.os.Handler;

public class MainActivity extends Activity {
    final int MARKER_UPDATE_INTERVAL = 60000; /* milliseconds */
    Handler handler = new Handler();
    Boolean cancel = false;
    Button btnTripStarted;
    Button showmap;
    Button ShowData,b4;
    Database mydb2;
    Runnable runnable;
    MapFragment mapFragment;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb2 = new Database(this);
         mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        btnTripStarted = (Button) findViewById(R.id.btnShowLocation);

        // show location button click event
        btnTripStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();

                runnable = new Runnable() {

                    @Override
                    public void run() {
                        try {

                                MarkPlace();



                        } catch (Exception e) {


                            // TODO: handle exception
                        } finally {
                            //also call the same runnable to call it at regular interval
                            handler.postDelayed(this, 60000);
                        }
                    }
                };
                handler.postDelayed(runnable, 60000);
            }
        });
        ShowData = (Button) findViewById(R.id.btnShowData);
        ShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = mydb2.getAlldata();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("lat: " + res.getString(1) + "\n");
                    buffer.append("lon: " + res.getString(2) + "\n");

                }
                showMessage("Data ", buffer.toString());
            }
        });
        showmap = (Button) findViewById(R.id.google);
        showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Google_map.class);
                startActivity(i);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        b4=(Button)findViewById(R.id.stop);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });

    }

    public void MarkPlace() {
        GPSTracker gps = new GPSTracker(MainActivity.this);
        {  // check if GPS enabled
            if (gps.canGetLocation()) {

                final double latitude = gps.getLatitude();
                final double longitude = gps.getLongitude();


                mydb2.insertData(latitude, longitude);


                // \n is for new line

            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }


        }
    }


    public void showMessage(String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();


    }

    public void stop() {

        handler.removeCallbacksAndMessages(runnable);
    }




}





