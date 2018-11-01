package com.csc301.team22.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonHome = findViewById(R.id.home);
        buttonHome.setOnClickListener(v -> Util.openActivity(this, MainActivity.class));
    }


    public void home(){Util.openActivity(this, RequestFrontpageActivity.class);}

}
