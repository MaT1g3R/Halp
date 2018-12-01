package com.csc301.team22.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.MockHTTPAdapter;

public class RequestSubmissionActivity extends AppCompatActivity {

    MockHTTPAdapter mock = MockHTTPAdapter.getInstance();
    private Button buttonHelpNow;
    private Button buttonHelpLater;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private String description, title;

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
        buttonHelpNow.setOnClickListener(v -> {
            title = editTextTitle.getText().toString();
            description = editTextDescription.getText().toString();
            SharedPreferences sharedPreferences = getSharedPreferences("calendar", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("title", title);
            editor.putString("desc", description);
            editor.apply();
            Util.openActivity(this, LookingForWorkerActivity.class);
        });
        buttonHelpLater.setOnClickListener(v -> {
            title = editTextTitle.getText().toString();
            description = editTextDescription.getText().toString();
            SharedPreferences sharedPreferences = getSharedPreferences("calendar", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("title", title);
            editor.putString("desc", description);
            editor.apply();
            Util.openActivity(this, CalendarActivity.class);
        });
    }

    private String getRequestTitle() {
        return Util.getEditTextString(editTextTitle, true);
    }

    private String getRequestDescription() {
        return Util.getEditTextString(editTextDescription, true);
    }

}
