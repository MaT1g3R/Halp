package com.csc301.team22.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class LookingForWorkerActivity extends AppCompatActivity {

    private ProgressBar spinner;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for_worker);
        cancel = findViewById(R.id.button5);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        cancel.setOnClickListener(v -> Util.openActivity(this,
                RequestSubmissionActivity.class));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(LookingForWorkerActivity.this,
                        FoundJobActivity.class);
                LookingForWorkerActivity.this.startActivity(mainIntent);
                LookingForWorkerActivity.this.finish();
            }
        }, 2000);
    }



}
