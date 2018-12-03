package com.csc301.team22.api.http;


import android.os.AsyncTask;

import com.csc301.team22.Request;
import com.csc301.team22.api.CreateRequest;
import com.csc301.team22.api.CreateUser;
import com.csc301.team22.api.JobRequest;
import com.csc301.team22.api.JobRequestList;
import com.csc301.team22.api.OptionalRequest;
import com.csc301.team22.api.OptionalWorker;
import com.csc301.team22.api.QueryBuilder;
import com.csc301.team22.api.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;

public class HTTPAdapter {
    private static HTTPAdapter instance = null;

    private final String URL = "https://halp.otonokizaka.moe/api/v1/";
    private final OkHttpClient client = new OkHttpClient();

    private String email = null;
    private String password = null;

    public static HTTPAdapter getInstance() {
        if (instance == null) {
            instance = new HTTPAdapter();
        }
        return instance;
    }

    private <T> String getResult(BaseHttpRequest httpRequest, AsyncTask<T, Void, String> result) throws HttpException {
        String resultString;
        try {
            resultString = result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new HttpException(500, e.getMessage());
        }
        HttpException httpException = httpRequest.getException();
        if (httpException != null) {
            throw httpException;
        }
        assert resultString != null;
        return resultString;
    }

    private String getDelete(String endpoint, Map<String, String> queries, boolean isGet) throws HttpException {
        HttpGetDelete httpGetDelete = new HttpGetDelete(client, URL + endpoint, email, password, isGet);
        AsyncTask<Map<String, String>, Void, String> result = httpGetDelete.execute(queries);
        return getResult(httpGetDelete, result);
    }

    private String get(String endpoint, Map<String, String> queries) throws HttpException {
        return getDelete(endpoint, queries, true);
    }

    private String get(String endpoint) throws HttpException {
        return get(endpoint, new HashMap<>());
    }

    private String delete(String endpoint, Map<String, String> queries) throws HttpException {
        return getDelete(endpoint, queries, false);
    }

    private String post(String endpoint, String postJson) throws HttpException {
        HttpPost httpPost = new HttpPost(client, URL + endpoint, email, password);
        AsyncTask<String, Void, String> result = httpPost.execute(postJson);
        return getResult(httpPost, result);
    }

    public User getUser(Integer userId) throws HttpException {
        Map<String, String> queries = new QueryBuilder().add("user_id", userId).build();
        String userProfile = get("profile", queries);
        return new Gson().fromJson(userProfile, User.class);
    }

    public User getUser() throws HttpException {
        return getUser(null);
    }

    public User authenticate(String email, String password) throws HttpException {
        this.email = email;
        this.password = password;
        try {
            return getUser();
        } catch (HttpException e) {
            this.email = null;
            this.password = null;
            throw e;
        }
    }


    public User createUser(CreateUser createUser) throws HttpException {
        String userProfile = post("create_user", createUser.toJson());
        return new Gson().fromJson(userProfile, User.class);
    }

    public String updateBio(String bio) throws HttpException {
        JSONObject postJson = new JSONObject();
        try {
            postJson.put("bio", bio);
            JSONObject bioResult = new JSONObject(post("update_bio", postJson.toString()));
            return bioResult.getString("bio");
        } catch (JSONException e) {
            throw new HttpException(400, e.getMessage());
        }
    }

    public List<JobRequest> getRequests(Map<String, String> queries) throws HttpException {
        String result = get("request", queries);
        return new Gson().fromJson(result, JobRequestList.class).getRequests();
    }

    public JobRequest createRequest(CreateRequest createRequest) throws HttpException {
        String result = post("request", createRequest.toJson());
        return new Gson().fromJson(result, JobRequest.class);
    }

    public String deleteRequest(int requestId) throws HttpException {
        String resp = delete("request", new QueryBuilder().add("request_id", requestId).build());
        assert resp != null;
        return resp;
    }

    public Request createResponse(int requestId, String comment) throws HttpException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("request_id", requestId);
            jsonObject.put("comment", comment);
        } catch (JSONException e) {
            throw new HttpException(400, e.getMessage());
        }
        String result = post("create_response", jsonObject.toString());
        return new Gson().fromJson(result, Request.class);
    }

    public Request assignWorker(int requestId) throws HttpException {
        return assignWorker(requestId, null);
    }

    public Request assignWorker(int requestId, Integer workerId) throws HttpException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("request_id", requestId);
            if (workerId != null) {
                jsonObject.put("worker_id", (int) workerId);
            }
        } catch (JSONException e) {
            throw new HttpException(400, e.getMessage());
        }
        String result = post("assign_worker", jsonObject.toString());
        return new Gson().fromJson(result, Request.class);
    }

    public Optional<User> findWorker(int requestId) throws HttpException {
        Map<String, String> queries = new QueryBuilder().add("request_id", requestId).build();
        String result = get("find_worker", queries);
        User optionalWorker = new Gson().fromJson(result, OptionalWorker.class).getWorker();
        return Optional.ofNullable(optionalWorker);
    }

    public Optional<JobRequest> findJob(Map<String, String> queries) throws HttpException {
        String result = get("find_job", queries);
        JobRequest optionalRequest = new Gson().fromJson(result, OptionalRequest.class).getRequest();
        return Optional.ofNullable(optionalRequest);
    }
}
