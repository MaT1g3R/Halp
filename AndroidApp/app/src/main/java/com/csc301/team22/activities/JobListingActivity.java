package com.csc301.team22.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.JobRequest;
import com.csc301.team22.api.http.HTTPAdapter;
import com.csc301.team22.api.http.HttpException;
import com.csc301.team22.fragments.RequestListFragment;

public class JobListingActivity extends AppCompatActivity {
    private final HTTPAdapter http = HTTPAdapter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_job);
        Button acceptJob = findViewById(R.id.acceptJob);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.requestList);
        if (!(fragment instanceof RequestListFragment)) throw new AssertionError();
        RequestListFragment requestListFragment = (RequestListFragment) fragment;
        acceptJob.setOnClickListener(v -> {
            final JobRequest job = requestListFragment.getSelectedJob();
            if (job == null) {
                Util.showError(this, "Please select a job to accept!", "");
                return;
            }
            try {
                http.assignWorker(job.getRequest_id());
            } catch (HttpException e) {
                Util.showError(this, "Failed to accept job", e.getMessage());
                return;
            }
            new AlertDialog.Builder(this)
                    .setTitle("Job Accepted!")
                    .setNeutralButton("Ok", (dialog, id) -> Util.openActivity(this, PostJobFindWorkActivity.class)).show();
        });
    }
}