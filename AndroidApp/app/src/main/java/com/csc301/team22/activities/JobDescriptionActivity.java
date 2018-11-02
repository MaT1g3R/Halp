package com.csc301.team22.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class JobDescriptionActivity extends AppCompatActivity {
    private Button Jason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);

        Jason = findViewById(R.id.button4);
        Jason.setOnClickListener(v -> Util.openActivity(this, FoundJobActivity.class));
    }
}
