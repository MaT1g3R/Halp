package com.csc301.team22.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.User;
import com.csc301.team22.api.http.HTTPAdapter;
import com.csc301.team22.api.http.HttpException;

public class ProfileActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    HTTPAdapter http = HTTPAdapter.getInstance();
    TextView first_name;
    TextView last_name;
    EditText bi_o;
    private Button buttonHome;
    private int user_id;
    private String first = "", last = "", bio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        first_name = findViewById(R.id.textView32);
        last_name = findViewById(R.id.textView34);
        bi_o = findViewById(R.id.editText14);

        buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(v -> goHome());

        try {
            User user = http.getUser();
            first = user.getFirst_name();
            last = user.getLast_name();
            bio = user.getBio();
            updateViews();
        } catch (HttpException e) {
            Util.showError(this, "Failed to get user profile", e.getMessage());
        }
    }


    public void updateViews() {
        first_name.setText(first);
        last_name.setText(last);
        bi_o.setText(bio);
    }

    public void goHome() {
        String newBio = bi_o.getText().toString();
        if (!newBio.equals(bio)) {
            try {
                http.updateBio(newBio);
                Util.openActivity(this, PostJobFindWorkActivity.class);
            } catch (HttpException e) {
                Util.showError(this, "Cannot set user bio", e.getMessage());
            }
        } else {
            Util.openActivity(this, PostJobFindWorkActivity.class);
        }
    }
}
