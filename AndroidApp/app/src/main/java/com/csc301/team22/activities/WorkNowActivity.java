package com.csc301.team22.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.csc301.team22.R;
import com.google.android.gms.maps.model.LatLng;

public class WorkNowActivity extends AppCompatActivity {
    private LatLng loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_now);
    }
}
