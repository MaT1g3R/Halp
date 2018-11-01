package com.csc301.team22;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

public class RequestFrontpage extends AppCompatActivity {
    private LinearLayout linearLayoutRequestList;
    private Button buttonPostRequest;
    private Button buttonCurrentRequests;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_frontpage);

        buttonPostRequest = findViewById(R.id.buttonPostRequest);
        buttonPostRequest.setOnClickListener(v -> Util.openActivity(this, RequestSubmissionActivity.class));

        buttonCurrentRequests = findViewById(R.id.buttonCurrentRequests);
        buttonCurrentRequests.setOnClickListener(v -> Util.openActivity(this, DetailedRequestList.class));

        linearLayoutRequestList = findViewById(R.id.linearLayoutRequestList);
        RequestManager.getInstance().addToLinearLayout(this, linearLayoutRequestList);
    }
}
