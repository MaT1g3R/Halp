package com.csc301.team22.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.csc301.team22.R;
import com.csc301.team22.Util;

public class MainActivity extends AppCompatActivity {
    private Button buttonLogin;
    private Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = findViewById(R.id.login);
        buttonLogin.setOnClickListener(v -> Util.openActivity(this, LoginActivity.class));

        buttonCreateAccount = findViewById(R.id.create_account);
        buttonCreateAccount.setOnClickListener(v -> Util.openActivity(this, CreateAccountActivity.class));
    }
}
