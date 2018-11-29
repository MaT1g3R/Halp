package com.csc301.team22.activities;

import com.csc301.team22.api.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csc301.team22.R;

import java.util.Iterator;

public class CreateAccountActivity extends AppCompatActivity {

    String first_name, last_name, email, pass, re_pass;
    String error = "Passwords must match";
    MockHTTPAdapter mock = MockHTTPAdapter.getInstance();

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        
        Button create = (Button) findViewById(R.id.button3);

        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                create_account();
            }
        });

//        loadData();
//        updateViews();
    }

    public void create_account(){

        EditText first = findViewById(R.id.createFirst);
        EditText last = findViewById(R.id.createLast);
        EditText mail = findViewById(R.id.createEmail);
        EditText password = findViewById(R.id.createPassword);
        EditText re_password = findViewById(R.id.createRepassword);
        TextView wrong = findViewById(R.id.wrongCreate);

        //D3 storing values
        first_name = first.getText().toString();
        last_name = last.getText().toString();
        email = mail.getText().toString();
        pass = password.getText().toString();
        re_pass = re_password.getText().toString();

        if (!pass.equals(re_pass)) {
            wrong.setText(error);
        } else {

            CreateUser newUser = new CreateUser.Builder().first_name(first_name)
                    .last_name(last_name).email(email).password(pass).build();

            User nUser = mock.createUser(newUser);

            Intent intent = new Intent(this, ProfileActivity.class);

            // Test
            intent.putExtra("CREATEID", nUser.getUser_id());

            startActivity(intent);
        }
    }
}
