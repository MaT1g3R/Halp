package com.csc301.team22.activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonHome;
    private ImageButton profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(v -> Util.openActivity(this, PostJobFindWorkActivity.class));

        profile = findViewById(R.id.buttonProfile);
        profile.setOnClickListener(v -> Util.openActivity(this, ProfileActivity.class));
    }


    public void home(){Util.openActivity(this, RequestFrontpageActivity.class);}

}
