package com.csc301.team22;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class DetailedRequestList extends AppCompatActivity {

    private LinearLayout linearLayoutRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_request_list);

        linearLayoutRequestList = findViewById(R.id.linearLayoutRequestList);
        RequestManager.getInstance().addToLinearLayout(this, linearLayoutRequestList);
    }
}
