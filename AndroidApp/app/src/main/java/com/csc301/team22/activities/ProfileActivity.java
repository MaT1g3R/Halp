package com.csc301.team22.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.*;

import android.content.SharedPreferences;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonHome;

    MockHTTPAdapter mock = MockHTTPAdapter.getInstance();
    private int user_id;
    private String first = "", last = "", bio = "";

    public static final String SHARED_PREFS = "sharedPrefs";

    TextView first_name;
    TextView last_name;
    EditText bi_o;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        first_name = findViewById(R.id.textView32);
        last_name = findViewById(R.id.textView34);
        bi_o = findViewById(R.id.editText14);

        buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(v -> goHome());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getInt("CREATEID");
            User user = mock.getProfile(user_id);

            first = user.getFirst_name();
            last = user.getLast_name();
            bio = user.getBio();

            first_name.setText(first);
            last_name.setText(last);
            bi_o.setText(bio);
            saveData();
        } else {
            loadData();
            updateViews();
        }

    }

        public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("ID", user_id);
        editor.putString("FIRST", first);
        editor.putString("LAST", last);
        editor.putString("BIO", bio);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        user_id = sharedPreferences.getInt("ID", mock.id_counter);
        first = sharedPreferences.getString("FIRST", "");
        last = sharedPreferences.getString("LAST", "");
        bio = sharedPreferences.getString("BIO", "");

    }

    public void updateViews() {
        first_name.setText(first);
        last_name.setText(last);
        bi_o.setText(bio);
    }

    public void goHome () {
        User user = mock.getProfile(user_id);
        bio = bi_o.getText().toString();
        user.setBio(bio);
        saveData();

        Util.openActivity(this, PostJobFindWorkActivity.class);
    }
}
