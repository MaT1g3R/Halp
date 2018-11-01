package com.csc301.team22.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.csc301.team22.PostJobFindWork;
import com.csc301.team22.R;

public class LoginActivity extends AppCompatActivity {
    private Button SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignIn = findViewById(R.id.email_sign_in_button);
        SignIn.setOnClickListener(v -> signIn());
    }

    public void signIn(){
        Intent intent = new Intent(this, PostJobFindWorkActivity.class);
        startActivity(intent);
    }
}
