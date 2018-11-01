package com.csc301.team22;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class PostJobFindWork extends AppCompatActivity {
    private Button postJob;
    private Button findWork;
    private Button buttonProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_find_work);

        postJob = findViewById(R.id.PostJobButton);
        postJob.setOnClickListener(v -> postJob());

        findWork = findViewById(R.id.FindWorkButton);
        findWork.setOnClickListener(v -> findWork());

        buttonProfile = findViewById(R.id.FindWorkProfileButton);
        buttonProfile.setOnClickListener(v -> openProfile());
    }

    public void postJob() {
        Util.openActivity(this, RequestFrontpage.class);
    }

    public void findWork() {
        //TODO: Jump to Work Finder page
    }

    public void openProfile() {
        //TODO: Jump to profile page
    }
}
