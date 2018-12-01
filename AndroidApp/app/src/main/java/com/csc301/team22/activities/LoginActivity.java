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
import com.csc301.team22.api.HTTPAdapter;
import com.csc301.team22.api.User;


public class LoginActivity extends AppCompatActivity {

    HTTPAdapter http = HTTPAdapter.getInstance();
    EditText TEmail, TPass;
    TextView errorBox;
    private String email = "", password = "", emptyError = "All fields must be filled";
    private String error = "Wrong email or password";

//    public class BagOfPrimitives {
//        public int value1 = 1;
//        public String value2 = "hi";
//        private transient int value3 = 3;
//        BagOfPrimitives() {
//
//            // no-args constructor
//        }
//    }


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
            User user = http.authenticate(email, password);
            if (user == null) {
                throw new IllegalAccessException();
            }
            Intent intent = new Intent(this, WorkNowWorkLaterActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            errorBox.setText("Invalid login info");
        }
    }
}


//    private void login() {
//        EditText username = findViewById(R.id.username);
//        EditText password = findViewById(R.id.password);
//
//        // check the combination of username and password
//        // will implement in part3
//
//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    MediaType JSON
//                            = MediaType.parse("application/json; charset=utf-8");
//
//
//                    BagOfPrimitives obj = new BagOfPrimitives();
//
//                    String json = new Gson().toJson(obj);
//                    assert json != null;
//
//
//                    OkHttpClient client = new OkHttpClient();
//
//                    RequestBody body = RequestBody.create(JSON, json);
//                    JobRequest request = new JobRequest.Builder()
//                            .url("https://httpbin.org/post")
//                            .post(body)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String resp_json = response.body().string();
//                    JSONObject Jobject = new JSONObject(resp_json);
//                    JSONObject hi = Jobject.getJSONObject("json");
//                    BagOfPrimitives respObj = new Gson().fromJson(hi.toString(), BagOfPrimitives.class);
//                    System.out.println(respObj.value1);
//                    System.out.println(respObj.value2);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();
//
//
//        Intent intent = new Intent(this, PostJobFindWorkActivity.class);
//        startActivity(intent);
//    }
