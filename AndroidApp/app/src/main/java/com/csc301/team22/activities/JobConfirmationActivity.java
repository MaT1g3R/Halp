package com.csc301.team22.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class JobConfirmationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button home;
    private ImageButton profile;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_confirmation);

        home = findViewById(R.id.buttonHome);
        home.setOnClickListener(v -> Util.openActivity(this, PostJobFindWorkActivity.class));

        profile = findViewById(R.id.buttonProfile);
        profile.setOnClickListener(v -> Util.openActivity(this, ProfileActivity.class));




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in worker, and move the camera.
        LatLng worker = new LatLng(43.66, -79.4);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        mMap.addMarker(new MarkerOptions().position(worker).title("Job Site"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(worker));
    }
}