package com.csc301.team22;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class RequestSubmissionActivity extends AppCompatActivity {

    private Button buttonHelpNow;
    private Button buttonHelpLater;
    private EditText editTextTitle;
    private EditText editTextDescription;

    private RequestManager requestManager;
    private View.OnClickListener createRequestNow = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO: Jump to Worker finder page
            requestManager.createRequest(getRequestTitle(), getRequestDescription(), null);
        }
    };

    private View.OnClickListener createRequestLater = new View.OnClickListener() {
        public void onClick(View v) {
            //TODO: Jump to calendar page
            requestManager.createRequest(getRequestTitle(), getRequestDescription(), new Date(0));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_submission);
        requestManager = RequestManager.getInstance();

        buttonHelpNow = findViewById(R.id.buttonHelpNow);
        buttonHelpLater = findViewById(R.id.buttonHelpLater);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);

        buttonHelpNow.setOnClickListener(createRequestNow);
        buttonHelpLater.setOnClickListener(createRequestLater);
    }

    private String getRequestTitle() {
        return Util.getEditTextString(editTextTitle, true);
    }

    private String getRequestDescription() {
        return Util.getEditTextString(editTextDescription, true);
    }

}
