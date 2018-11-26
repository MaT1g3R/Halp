package com.csc301.team22.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.csc301.team22.R;
import com.csc301.team22.Util;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    public class BagOfPrimitives {
        public int value1 = 1;
        public String value2 = "hi";
        private transient int value3 = 3;
        BagOfPrimitives() {

            // no-args constructor
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(v -> login());

        Button CreateAccount = findViewById(R.id.create_account);
        CreateAccount.setOnClickListener(v -> Util.openActivity(this, CreateAccountActivity.class));


    }

    private void login() {
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        // check the combination of username and password
        // will implement in part3

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    MediaType JSON
                            = MediaType.parse("application/json; charset=utf-8");


                    BagOfPrimitives obj = new BagOfPrimitives();

                    String json = new Gson().toJson(obj);
                    assert json != null;


                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url("https://httpbin.org/post")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String resp_json = response.body().string();
                    JSONObject Jobject = new JSONObject(resp_json);
                    JSONObject hi = Jobject.getJSONObject("json");
                    BagOfPrimitives respObj = new Gson().fromJson(hi.toString(), BagOfPrimitives.class);
                    System.out.println(respObj.value1);
                    System.out.println(respObj.value2);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


        Intent intent = new Intent(this, PostJobFindWorkActivity.class);
        startActivity(intent);
    }
}
