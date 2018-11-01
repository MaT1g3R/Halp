package com.csc301.team22.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csc301.team22.R;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button create = (Button) findViewById(R.id.button3);

        create.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                create_account();
            }
        });

    }

    public void create_account(){
        boolean flag = true;
        EditText username = findViewById(R.id.editText3);
        EditText password = findViewById(R.id.editText6);
        EditText re_password = findViewById(R.id.editText7);
        // check condition
        // will implement in part3

        if(flag){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
