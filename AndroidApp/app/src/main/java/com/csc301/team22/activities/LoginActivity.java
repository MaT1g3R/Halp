package com.csc301.team22.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.csc301.team22.api.http.HTTPAdapter;
import com.csc301.team22.api.http.HttpException;


public class LoginActivity extends AppCompatActivity {

    HTTPAdapter http = HTTPAdapter.getInstance();
    EditText TEmail, TPass;
    TextView errorBox;
    private String email = "", password = "", emptyError = "All fields must be filled";
    private String error = "Wrong email or password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> login());

        Button CreateAccount = findViewById(R.id.loginCreateButton);
        CreateAccount.setOnClickListener(v -> Util.openActivity(this, CreateAccountActivity.class));
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    private void login() {
        TEmail = findViewById(R.id.loginEmail);
        TPass = findViewById(R.id.loginPassword);
        errorBox = findViewById(R.id.textView2);

        email = TEmail.getText().toString();
        password = TPass.getText().toString();

        if (email.equals("") || password.equals("")) {
            errorBox.setText(emptyError);
        }

        try {
            http.authenticate(email, password);
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } catch (HttpException e) {
            errorBox.setText("Invalid login info");
        }
    }
}
