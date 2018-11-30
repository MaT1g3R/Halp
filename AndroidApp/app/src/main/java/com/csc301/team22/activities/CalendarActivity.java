package com.csc301.team22.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.csc301.team22.R;
import com.csc301.team22.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    TextView chooseTime, pickTime;
    TimePickerDialog timePickerDialog;
    Calendar cal, calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    private Button submit;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Spinner spinnerDuration;

    // D3 storing values
    private String saveDate;
    private int start_time, duration;


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
        submit.setOnClickListener(v -> {

            saveDate = mDisplayDate.getText().toString();
            start_time = Integer.parseInt(chooseTime.getText().toString());

            Util.openActivity(this, JobDescriptionActivity.class);
        });

        spinnerDuration = findViewById(R.id.spinnerDud);
        List<Integer> hours = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            hours.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, hours);

        spinnerDuration.setAdapter(adapter);


        // Display the date
        mDisplayDate = (TextView) findViewById(R.id.calendartv);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CalendarActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("CalendarActivity", "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                mDisplayDate.setText(sdf.format(cal.getTime()));
//                mDisplayDate.setText((cal.getTime()).toString());


                // dd/mm/yyyy format
                String date = day + "/" + month + "/" + year;
//                mDisplayDate.setText(date);
            }
        };

        // "From" time
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
                    chooseTime.setText(String.format("%d:%02d ", hourOfDay, minutes) + amPm);
                }
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });

//        // "To" time
//        pickTime = findViewById(R.id.calendarTo);
//        pickTime.setOnClickListener(view -> {
//            calendar = Calendar.getInstance();
//            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
//            currentMinute = calendar.get(Calendar.MINUTE);
//
//            timePickerDialog = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                @Override
//                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
//                    if (hourOfDay >= 12) {
//                        amPm = "PM";
//                        hourOfDay -= 12;
//                    } else {
//                        amPm = "AM";
//                    }
//                    pickTime.setText(String.format("%d:%d ", hourOfDay, minutes) + amPm);
//                }
//            }, currentHour, currentMinute, false);
//
//            timePickerDialog.show();
//        });
    }
}
