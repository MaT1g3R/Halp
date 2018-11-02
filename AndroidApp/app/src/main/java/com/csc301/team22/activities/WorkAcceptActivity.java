package com.csc301.team22.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class WorkAcceptActivity extends AppCompatActivity {
    private Button accept, decline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_accept);

        accept = findViewById(R.id.acceptButton);
        accept.setOnClickListener(v -> Util.openActivity(this, JobConfirmationActivity.class));

        decline = findViewById(R.id.declineButton);
        decline.setOnClickListener(v -> Util.openActivity(this, WorkNowWorkLaterActivity.class));
    }
}
