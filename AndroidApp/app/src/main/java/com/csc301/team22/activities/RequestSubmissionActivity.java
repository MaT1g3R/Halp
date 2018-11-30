package com.csc301.team22.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.*;

public class RequestSubmissionActivity extends AppCompatActivity {

    private Button buttonHelpNow;
    private Button buttonHelpLater;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private String description, title;
    MockHTTPAdapter mock = MockHTTPAdapter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_submission);

        buttonHelpNow = findViewById(R.id.buttonHelpNow);
        buttonHelpLater = findViewById(R.id.buttonHelpLater);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);

        // D3
        description = editTextDescription.getText().toString();
        title = editTextTitle.getText().toString();

//        CreateUser newUser = new CreateUser.Builder().first_name("jeb")
//                .last_name("jeb").email("jeb").password("jeb").build();
//
//        User user = mock.createUser(newUser);
//
//        JobRequest newr = new JobRequest.Builder().request_id(2).start_time(17)
//                .duration(2).customer(user).latitude(30).longitude(50)
//                .description("jebjebjeb jeb").build();

//        mock.jobRequests.add(newr);

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
