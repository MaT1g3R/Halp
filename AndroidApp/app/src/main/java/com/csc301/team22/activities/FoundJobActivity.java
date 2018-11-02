package com.csc301.team22.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class FoundJobActivity extends AppCompatActivity {
    private Button accept, decline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_job);

        accept = findViewById(R.id.buttonAccept);
        accept.setOnClickListener(v -> Util.openActivity(this, WorkerFoundConfirmationActivity.class));

        //TODO: Go to page #7
        decline = findViewById(R.id.buttonDecline);
        decline.setOnClickListener(v -> Util.openActivity(this, LookingForWorkerActivity.class));
    }
}
