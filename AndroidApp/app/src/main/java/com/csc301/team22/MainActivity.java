package com.csc301.team22;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        createAccount = (Button) findViewById(R.id.create_account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccount();
            }
        });
    }

        public void openLogin(){
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        }

        public void openCreateAccount(){
            Intent intent = new Intent(this, create_account.class);
            startActivity(intent);
        }




}
