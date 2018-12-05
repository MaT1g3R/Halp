package com.csc301.team22.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.CreateRequest;
import com.csc301.team22.api.http.HTTPAdapter;
import com.csc301.team22.api.http.HttpException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String SHARED_PREFS = "sharedPrefs";
    HTTPAdapter http = HTTPAdapter.getInstance();
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
    private long saveDate, start_time;
    private int duration;

    private String title, description;

    private GoogleMap mMap;
    private LatLng sydney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

//        Bundle extras = getIntent().getExtras();
//        assert extras != null;
//        title = extras.getString("title");
//        description = extras.getString("description");
//        assert title != null;
//        assert description != null;

        submit = findViewById(R.id.submitButton);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        submit.setOnClickListener(v -> {

            // Add the date and time together to make them a single varirable
            start_time += saveDate;


            // Use this block on every page to get user_id and User
            SharedPreferences id = getSharedPreferences(SHARED_PREFS,
                    MODE_PRIVATE);


            SharedPreferences sharedPreferences = getSharedPreferences("calendar", MODE_PRIVATE);

            title = sharedPreferences.getString("title", "default");
            description = sharedPreferences.getString("desc", "default");

            CreateRequest newr = new CreateRequest.Builder().start_time(start_time)
                    .duration(duration * 3600).latitude(sydney.latitude)
                    .longitude(sydney.longitude).description(description).title(title).build();

            try {
                http.createRequest(newr);
                Util.openActivity(this, PostJobFindWorkActivity.class);
            } catch (HttpException e) {
                Util.showError(this, "Cannot Create Request", e.getMessage());
            }
        });

        spinnerDuration = findViewById(R.id.spinnerDud);
        List<Integer> hours = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            hours.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, hours);

        spinnerDuration.setAdapter(adapter);

        spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duration = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                duration = 1;
            }
        });

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
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
//                Log.d("CalendarActivity", "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                String date = month + "-" + day + "-" + year;
                mDisplayDate.setText(date);
//                mDisplayDate.setText((cal.getTi   me()).toString());

                try {
                    Date test = sdf.parse(date);
                    System.out.println(test.getTime() / 1000);
                    saveDate = test.getTime() / 1000;
                    System.out.println(saveDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


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
                    int unsetHour = hourOfDay;
                    if (hourOfDay >= 12) {
                        amPm = "PM";
                        hourOfDay -= 12;
                    } else {
                        amPm = "AM";
                    }
                    chooseTime.setText(String.format("%d:%02d ", hourOfDay, minutes) + amPm);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

                    try {
                        Date time = timeFormat.parse(String.format("%d:%02d ", unsetHour, minutes));
                        start_time = time.getTime() / 1000;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        sydney = new LatLng(44.6, -79.4);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Spadina"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}