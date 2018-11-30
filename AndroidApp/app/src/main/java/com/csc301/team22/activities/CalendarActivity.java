package com.csc301.team22.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.csc301.team22.R;
import com.csc301.team22.Util;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    TextView chooseTime, pickTime;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    private Button submit;
    private Spinner dateSpin;

//    Calendar caltest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

//        Bundle extras = getIntent().getExtras();
//        assert extras != null;
//        String title = extras.getString("title");
//        String description = extras.getString("description");
//        assert title != null;
//        assert description != null;

        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(v -> Util.openActivity(this, JobDescriptionActivity.class));

//        dateSpin = findViewById(R.id.daySpinner);
//        String[] days = new String[]{"Monday", "Tuesday", "Wednesday",
//                "Thursday", "Friday", "Saturday", "Sunday"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_dropdown_item, days);
//
//        dateSpin.setAdapter(adapter);

//        caltest = findViewById(R.id.calendarView);

        chooseTime = findViewById(R.id.calendarFrom);
        chooseTime.setOnClickListener(view -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    if (hourOfDay >= 12) {
                        amPm = "PM";
                        hourOfDay -= 12;
                    } else {
                        amPm = "AM";
                    }
                    chooseTime.setText(String.format("%d:%d ", hourOfDay, minutes) + amPm);
                }
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });

        pickTime = findViewById(R.id.calendarTo);
        pickTime.setOnClickListener(view -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    if (hourOfDay >= 12) {
                        amPm = "PM";
                        hourOfDay -= 12;
                    } else {
                        amPm = "AM";
                    }
                    pickTime.setText(String.format("%d:%d ", hourOfDay, minutes) + amPm);
                }
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });
    }
}
