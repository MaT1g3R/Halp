package com.csc301.team22.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.*;

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

//        Request request = new Request.Builder().request_id(2).build();
//
//        ScrollView scroll = findViewById(R.id.frontScroll);
//        LinearLayout ll = new LinearLayout(this);
//
//        ll.setOrientation(LinearLayout.VERTICAL);
//        scroll.addView(ll);
//
//        TextView tv = new TextView(this);
//        tv.setText("Test");
//        ll.addView(tv);
//
////        EditText et = new EditText(this);
////        et.setText("ET test");
////        ll.addView(et);
//
////        this.setContentView(scroll);
    }
}
