package com.example.lenovo.Location4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Google_map extends AppCompatActivity implements OnMapReadyCallback {
Database mydb3,mydb4;
    GoogleMap map2;
    Button b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_google_map);
        b2=(Button) findViewById(R.id.remove);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map2.clear();
                mydb3.DeleteAll();

            }
        });
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    @Override
    public void onMapReady(GoogleMap map) {
        map2=map;
        mydb4=mydb3;
        mydb3=new Database(this);
        Cursor res=mydb3.getAlldata();
        while(res.moveToNext()) {
            LatLng Gurdaspur = new LatLng(res.getDouble(1), res.getDouble(2));
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Gurdaspur, 13));

            map.addMarker(new MarkerOptions()
                    .title("Gurdaspur")
                    .snippet("city")
                    .position(Gurdaspur));
        }





    }





}
