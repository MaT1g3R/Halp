package com.csc301.team22.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.HTTPAdapter;
import com.csc301.team22.api.User;

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
            User user = http.authenticate(http.email, http.password);
            first = user.getFirst_name();
            last = user.getLast_name();
            bio = user.getBio();
            updateViews();
        } catch (Exception e) {

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
            http.updateBio(newBio);
        }
        Util.openActivity(this, PostJobFindWorkActivity.class);
    }
}
