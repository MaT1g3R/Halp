package com.csc301.team22;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

public class activity_request_frontpage extends AppCompatActivity {
    private LinearLayout linearLayoutRequestList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_frontpage);

        linearLayoutRequestList = findViewById(R.id.linearLayoutRequestList);
        setupRequests(requestList());
    }

    private List<Request> requestList() {
        return Arrays.asList(
                new Request("Mowing the Lawn", "I need help mowing my lawn and doing some minor landscaping. Should only take a few hours if both of us are at it......"),
                new Request("Helping Move Dishwasher", ""),
                new Request("3-Day Manual Labor", ""),
                new Request("Garbage Disposal", "")
        );
    }

    private void setupRequests(List<Request> requests) {
        for (Request request : requests) {
            RequestCardView requestCardView = new RequestCardView(this, request.getName(), request.getDescription());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 10, 0);
            linearLayoutRequestList.addView(requestCardView.getLayout(), lp);
        }
    }
}
