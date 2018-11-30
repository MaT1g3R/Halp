package com.csc301.team22.activities;

import com.csc301.team22.api.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csc301.team22.R;


public class CreateAccountActivity extends AppCompatActivity {

    String first_name, last_name, email, pass, re_pass;
    String error = "Passwords must match";
    String emptyError = "All fields must be filled";
    HTTPAdapter http = HTTPAdapter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        
        Button create = findViewById(R.id.button3);

        create.setOnClickListener(v -> create_account());
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

        // Checking if entries are valid
        if (!re_pass.equals(pass)) {
            wrong.setText(error);
        } else if (pass.equals("") || first_name.equals("")
                || last_name.equals("") || email.equals("")) {
            wrong.setText(emptyError);
        } else {

            CreateUser newUser = new CreateUser.Builder().first_name(first_name)
                    .last_name(last_name).email(email).password(pass).build();

            User nUser = http.createUser(newUser);

            Intent intent = new Intent(this, ProfileActivity.class);

            // Passing user_id to next activity
            intent.putExtra("CREATEID", nUser.getUser_id());

            startActivity(intent);
        }
    }
}
