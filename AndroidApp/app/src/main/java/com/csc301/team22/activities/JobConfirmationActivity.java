package com.csc301.team22.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class JobConfirmationActivity extends AppCompatActivity {

    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_confirmation);

        home = findViewById(R.id.buttonHome);
        home.setOnClickListener(v -> Util.openActivity(this, MainActivity.class));
    }








}
