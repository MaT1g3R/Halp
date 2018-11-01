package com.csc301.team22.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        Button CreateAccount = findViewById(R.id.create_account);
        CreateAccount.setOnClickListener(v -> Util.openActivity(this, CreateAccountActivity.class));


    }

    private void login(){
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        // check the combination of username and password
        // will implement in part3

        Intent intent = new Intent(this, PostJobFindWorkActivity.class);
        startActivity(intent);
    }
}
