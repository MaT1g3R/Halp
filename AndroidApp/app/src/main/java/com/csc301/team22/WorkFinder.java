package com.csc301.team22;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkFinder extends AppCompatActivity {
    private Button WorkNow;
    private Button WorkLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_finder);

        WorkNow = (Button) findViewById(R.id.WorkNowButton);
        WorkNow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View s) {
                workNow();
            }
        });

        WorkLater = (Button) findViewById(R.id.WorkLaterButton);
        WorkLater.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View s) {
                workLater();
            }
        });

    }

    public void workNow() {
        //TODO: Jump to
        Intent intent = new Intent(getApplicationContext(), PostJobFindWork.class);
        startActivity(intent);
    }

    public void workLater() {
        //TODO: Jump to
        Intent intent = new Intent(getApplicationContext(), PostJobFindWork.class);
        startActivity(intent);
    }
}
