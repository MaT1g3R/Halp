package com.csc301.team22.activities;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.csc301.team22.R;
import com.csc301.team22.Util;

import java.util.Calendar;

public class JobAvailability extends AppCompatActivity {

    EditText chooseTime, pickTime;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    CalendarView calendarInput;
    int currentHour;
    int currentMinute;
    String amPm;
    Button submit;
    Button use_profile;
    long pickedTimeFrom= 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_availability);
        submit = findViewById(R.id.button);
        use_profile = findViewById(R.id.button2);
        calendarInput = findViewById(R.id.calendarView);

        submit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Long date = calendarInput.getDate() / 1000;
            date += pickedTimeFrom;
            bundle.putLong("from_datetime", date);
            Util.openActivity(this,
                    JobListingActivity.class, bundle);
        });


        use_profile.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Long date = calendarInput.getDate() / 1000;
            date += pickedTimeFrom;
            bundle.putLong("from_datetime", date);
            Util.openActivity(this,
                    JobListingActivity.class, bundle);
        });


        chooseTime = findViewById(R.id.editText8);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(JobAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            hourOfDay -= 12;
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        pickedTimeFrom = (long) hourOfDay * 3600 + minutes * 60;
                        chooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        pickTime = findViewById(R.id.editText9);
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(JobAvailability.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            hourOfDay -= 12;
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        pickTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();

            }
        });
    }
}
