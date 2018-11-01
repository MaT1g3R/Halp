package com.csc301.team22.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

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
        loginButton.setOnClickListener(v -> Util.openActivity(this, PostJobFindWorkActivity.class));

        Button CreateAccount = findViewById(R.id.create_account);
        CreateAccount.setOnClickListener(v -> Util.openActivity(this, CreateAccountActivity.class));


    }
}
