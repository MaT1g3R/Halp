package com.csc301.team22;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PostJobFindWork extends AppCompatActivity {
    private Button PostJob;
    private Button FindWork;
    private Button Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_find_work);

        PostJob = (Button) findViewById(R.id.PostJobButton);
        PostJob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View s) {
                postJob();
            }
        });

        FindWork = (Button) findViewById(R.id.FindWorkButton);
        FindWork.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View s) {
                findWork();
            }
        });

        Profile = (Button) findViewById(R.id.FindWorkProfileButton);
        Profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View s) {
                openProfile();
            }
        });
    }

    public void postJob() {
        Intent intent = new Intent(getApplicationContext(), RequestFrontpage.class);
        startActivity(intent);
    }

    public void findWork() {
        //TODO: Jump to Work Finder page
        Intent intent = new Intent(getApplicationContext(), RequestFrontpage.class);
        startActivity(intent);
    }

    public void openProfile() {
        //TODO: Jump to Profile page
        Intent intent = new Intent(getApplicationContext(), RequestFrontpage.class);
        startActivity(intent);
    }
}
