package com.csc301.team22.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class RequestFrontpageActivity extends AppCompatActivity {
    private Button buttonPostRequest;
    private Button buttonCurrentRequests;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_frontpage);

        buttonPostRequest = findViewById(R.id.buttonPostRequest);
        buttonPostRequest.setOnClickListener(v -> Util.openActivity(this, RequestSubmissionActivity.class));

        buttonCurrentRequests = findViewById(R.id.buttonCurrentRequests);
        buttonCurrentRequests.setOnClickListener(v -> Util.openActivity(this, DetailedRequestListActivity.class));

    }
}
