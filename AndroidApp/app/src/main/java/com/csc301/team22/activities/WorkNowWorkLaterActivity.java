package com.csc301.team22.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;


public class WorkNowWorkLaterActivity extends AppCompatActivity {

    private Button buttonWorkNow;
    private Button buttonWorkLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worknow_worklater);

        buttonWorkNow = findViewById(R.id.buttonWorkNow);
        buttonWorkLater = findViewById(R.id.buttonWorkLater);

        buttonWorkNow.setOnClickListener(v -> Util.openActivity(this, WorkAcceptActivity.class));
        buttonWorkLater.setOnClickListener(v -> Util.openActivity(this, JobAvailability.class));
    }


}
