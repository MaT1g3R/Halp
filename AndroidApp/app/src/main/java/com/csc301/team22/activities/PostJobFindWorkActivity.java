package com.csc301.team22.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class PostJobFindWorkActivity extends AppCompatActivity {
    private Button postJob;
    private Button findWork;
    private Button buttonProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_find_work);

        postJob = findViewById(R.id.PostJobButton);
        postJob.setOnClickListener(v -> Util.openActivity(this, RequestFrontpageActivity.class));

        findWork = findViewById(R.id.FindWorkButton);
        findWork.setOnClickListener(v -> Util.openActivity(this, WorkNowWorkLaterActivity.class));

        buttonProfile = findViewById(R.id.FindWorkProfileButton);
        buttonProfile.setOnClickListener(v -> Util.openActivity(this, ProfileActivity.class));
    }
}
