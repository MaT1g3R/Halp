package com.csc301.team22.activities;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class JobConfirmationActivity extends AppCompatActivity {

    private Button home;
    private ImageButton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_confirmation);

        home = findViewById(R.id.buttonHome);
        home.setOnClickListener(v -> Util.openActivity(this, PostJobFindWorkActivity.class));

        profile = findViewById(R.id.buttonProfile);
        profile.setOnClickListener(v -> Util.openActivity(this, ProfileActivity.class));


    }

}
