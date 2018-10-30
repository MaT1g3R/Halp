package com.csc301.team22;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class RequestFrontpage extends AppCompatActivity {
    private LinearLayout linearLayoutRequestList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_frontpage);

        linearLayoutRequestList = findViewById(R.id.linearLayoutRequestList);
        RequestManager.getInstance().addToLinearLayout(this, linearLayoutRequestList);
    }
}
