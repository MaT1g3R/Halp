package com.csc301.team22.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class WorkNowWorkLaterActivity extends AppCompatActivity {

    private Button buttonWorkNow;
    private Button buttonWorkLater;
    private Spinner spinnerDuration;
    int distance;
    LatLng loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worknow_worklater);

        buttonWorkNow = findViewById(R.id.buttonWorkNow);
        buttonWorkLater = findViewById(R.id.buttonWorkLater);

        loc = new LatLng(43.58, -79.41);

        SharedPreferences sP = getSharedPreferences("loc", MODE_PRIVATE);
        SharedPreferences.Editor edit = sP.edit();

        String lati = ((Double) loc.latitude).toString();
        String longi = ((Double) loc.longitude).toString();
        edit.putString("lati", lati);
        edit.putString("longi", longi);
        edit.apply();

        buttonWorkNow.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = getSharedPreferences("distance", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("dist", distance);
            editor.apply();

            Util.openActivity(this, WorkNowActivity.class);
        });

        buttonWorkLater.setOnClickListener(v -> Util.openActivity(this, JobAvailability.class));

        spinnerDuration = findViewById(R.id.spinnerDud2);
        List<Integer> hours = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            if (i < 6 || i == 10 || i == 15 || i == 20 || i == 25 || i == 30) {
                hours.add(i);
            }
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, hours);

        spinnerDuration.setAdapter(adapter);

        spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distance = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                distance = 1;
            }
        });
    }


}
