package com.csc301.team22.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.csc301.team22.R;
import com.csc301.team22.Util;

import java.util.Calendar;

public class JobAvailability extends AppCompatActivity {

    EditText chooseTimeFrom, chooseTimeTo;
    TimePickerDialog timePickerDialog;
    CalendarView calendarInput;
    int currentHour;
    int currentMinute;
    Button submit;
    Button use_profile;
    long pickedTimeFrom = 0;
    long date = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_availability);
        submit = findViewById(R.id.button);
        use_profile = findViewById(R.id.button2);
        calendarInput = findViewById(R.id.calendarView);
        calendarInput.setOnDateChangeListener((view, year, month, day) -> {
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            pickedTimeFrom = c.getTimeInMillis() / 1000;
        });

        submit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("from_datetime", date + pickedTimeFrom);
            Util.openActivity(this,
                    JobListingActivity.class, bundle);
        });


        use_profile.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("from_datetime", date + pickedTimeFrom);
            Util.openActivity(this,
                    JobListingActivity.class, bundle);
        });

        chooseTimeFrom = findViewById(R.id.chooseTimeFrom);
        chooseTimeFrom.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(
                    JobAvailability.this,
                    (timePicker, hourOfDay, minutes) -> {
                        JobAvailability.this.pickedTimeFrom = (long) hourOfDay * 3600 + minutes * 60;
                        chooseTimeFrom.setText(String.format("%02d:%02d", hourOfDay, minutes));
                    },
                    currentHour,
                    currentMinute,
                    true
            );
            timePickerDialog.show();
        });

        chooseTimeTo = findViewById(R.id.chooseTimeTo);
        chooseTimeTo.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(
                    JobAvailability.this,
                    (timePicker, hourOfDay, minutes) -> chooseTimeTo.setText(String.format("%02d:%02d", hourOfDay, minutes)),
                    currentHour,
                    currentMinute,
                    true
            );
            timePickerDialog.show();
        });
    }
}
