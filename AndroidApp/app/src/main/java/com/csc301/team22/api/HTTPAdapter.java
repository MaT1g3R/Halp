package com.csc301.team22.api;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPAdapter implements IHTTPAdapter {

    private static HTTPAdapter instance = null;
    private final String URL = "http://10.0.2.2:8000/api/v1/";
    // Singleton
    public String email;
    public String password;

    public static HTTPAdapter getInstance() {
        if (instance == null) {
            instance = new HTTPAdapter();
        }

        return instance;
    }


    public User getProfile(int user_id) {

        return null;
    }

    private String httpPost(String endpoint, String json) {
        final ArrayList<String> respObj = new ArrayList<>();
        Thread thread = new Thread(() -> {
            try {
                MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");

                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, json);

                String email_password = email + ':' + password;
                byte[] encodedBytes = Base64.getEncoder().encode(email_password.getBytes());
                String header = "Basic " + new String(encodedBytes);

                Request request = new Request.Builder()
                        .url(URL + endpoint)
                        .header("Authorization", header)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                String resp_json = response.body().string();
                respObj.add(resp_json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return respObj.get(0);
    }

    private String httpGet(String endpoint, HashMap<String, String> query) throws InterruptedException {
        final ArrayList<String> respObj = new ArrayList<>();
        Thread thread = new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            String email_password = email + ':' + password;
            byte[] encodedBytes = Base64.getEncoder().encode(email_password.getBytes());
            String header = "Basic " + new String(encodedBytes);
            StringBuilder params = new StringBuilder();

            for (Map.Entry<String, String> entry : query.entrySet()) {
                params.append(entry.getKey());
                params.append("=");
                params.append(entry.getValue());
                params.append("&");
            }
            Request request = new Request.Builder()
                    .url(URL + endpoint + "?" + params.toString())
                    .addHeader("Authorization", header)
                    .addHeader("Content-Type", "application/json")
                    .get()
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            String resp_json = null;
            try {
                resp_json = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            respObj.add(resp_json);
        });

        thread.start();
        thread.join();
        return respObj.get(0);
    }

    public User createUser(CreateUser user) {
        email = user.getEmail();
        password = user.getPassword();
        String json = new Gson().toJson(user);
        String endpoint = "create_user";
        assert json != null;
        String respJson = httpPost(endpoint, json);
        return new Gson().fromJson(respJson, User.class);
    }

    public void updateBio(String bio) {
        httpPost("update_bio", "{\"bio\": \"" + bio + "\"}");
    }

    public List<JobRequest> getRequests(RequestQuery query) {
        return null;
    }

    public JobRequest createRequest(CreateRequest createrequest) {
        return null;
    }


    public void deleteRequest(int requestId) {

    }


    public User findWorker(int userId) {
        return null;
    }


    public JobRequest findJob(Integer duration, Integer radius, Double latitude, Double longitude) {
        return null;
    }

    @Override
    public User authenticate(String email, String password) throws InterruptedException {
        this.email = email;
        this.password = password;
        String endpoint = "profile";
        String jsonResp = httpGet(endpoint, new HashMap<>());
        User user = new Gson().fromJson(jsonResp, User.class);
        if (user.getFirst_name().trim().equals("") || user.getLast_name().trim().equals("")) {
            return null;
        }
        return user;
    }
}
