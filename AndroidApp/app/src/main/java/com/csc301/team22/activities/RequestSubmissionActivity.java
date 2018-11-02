package com.csc301.team22.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class RequestSubmissionActivity extends AppCompatActivity {

    private Button buttonHelpNow;
    private Button buttonHelpLater;
    private EditText editTextTitle;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_submission);

        buttonHelpNow = findViewById(R.id.buttonHelpNow);
        buttonHelpLater = findViewById(R.id.buttonHelpLater);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);

        //TODO: Make help now go to found worker page
        buttonHelpNow.setOnClickListener(v ->{
            Bundle bundle = new Bundle();
            bundle.putString("title", getRequestTitle());
            bundle.putString("description", getRequestDescription());
            Util.openActivity(this, LookingForWorkerActivity.class, bundle);
        });
        buttonHelpLater.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", getRequestTitle());
            bundle.putString("description", getRequestDescription());
            Util.openActivity(this, CalendarActivity.class, bundle);
        });
    }

    private String getRequestTitle() {
        return Util.getEditTextString(editTextTitle, true);
    }

    private String getRequestDescription() {
        return Util.getEditTextString(editTextDescription, true);
    }

}
