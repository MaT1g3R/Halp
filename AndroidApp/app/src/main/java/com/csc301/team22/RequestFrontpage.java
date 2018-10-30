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
        setupRequests(RequestManager.getInstance().getRequests());
    }

    private void setupRequests(Iterable<Request> requests) {
        for (Request request : requests) {
            RequestCardView requestCardView = new RequestCardView(this, request.getName(), request.getDescription());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 10, 0);
            linearLayoutRequestList.addView(requestCardView.getLayout(), lp);
        }
    }
}
