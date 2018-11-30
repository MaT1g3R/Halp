package com.csc301.team22.api;



import com.google.gson.Gson;

import java.util.Base64;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPAdapter implements IHTTPAdapter{

    // Singleton
    private static HTTPAdapter instance = null;

    public static HTTPAdapter getInstance() {
        if(instance == null) {
            instance = new HTTPAdapter();
        }

        return instance;
    }

    
    public User getProfile(int user_id) {

        return null;
    }


    public User createUser(CreateUser user) {

        String email_password = user.getEmail() + ':' + user.getPassword();
        byte[] encodedBytes = Base64.getEncoder().encode(email_password.getBytes());
        String header = new String(encodedBytes);
        final User[] respObj = new User[1];

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    MediaType JSON
                            = MediaType.parse("application/json; charset=utf-8");

                    String json = new Gson().toJson(user);
                    assert json != null;
                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = RequestBody.create(JSON, json);

                    Request request = new Request.Builder()
                            .url("localhost:8000/api/v1/create_user")
                            .header("Basic ",header)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    assert response.body() != null;
                    String resp_json = response.body().string();
                    respObj[0] = new Gson().fromJson(resp_json, User.class);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        return respObj[0];
    }


    public String updateBio(String bio) {
        return null;
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


    public int authenticate(String email, String password) {
        return -1;
    }
}

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
////                            .build();
////                    Response response = client.newCall(request).execute();
////                    String resp_json = response.body().string();
////                    JSONObject Jobject = new JSONObject(resp_json);
////                    JSONObject hi = Jobject.getJSONObject("json");
////                    BagOfPrimitives respObj = new Gson().fromJson(hi.toString(), BagOfPrimitives.class);
////                    System.out.println(respObj.value1);
////                    System.out.println(respObj.value2);
////
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        });
////
////        thread.start();
