package com.csc301.team22.api.http;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

abstract class BaseHttpRequest<T, U, V> extends AsyncTask<T, U, V> {
    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    HttpException exception = null;
    String url;
    private OkHttpClient client;
    private String email;
    private String password;

    BaseHttpRequest(OkHttpClient client, String url, String email, String password) {
        super();
        this.client = client;
        this.email = email;
        this.password = password;
        this.url = url;
    }

    public HttpException getException() {
        return exception;
    }


    Optional<String> authHeader() {
        if (email == null || password == null) {
            return Optional.empty();
        }
        String email_password = email + ':' + password;
        byte[] encodedBytes = Base64.getEncoder().encode(email_password.getBytes());
        String header = "Basic " + new String(encodedBytes);
        return Optional.of(header);
    }

    @Nullable
    String makeRequest(Request request) {
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            this.exception = new HttpException(500, e.getMessage());
            return null;
        }
        try (ResponseBody respBody = response.body()) {
            if (respBody == null) {
                this.exception = new HttpException(500, "HTTP response body was null");
                return null;
            }
            String respJson;
            try {
                respJson = respBody.string();
            } catch (IOException e) {
                this.exception = new HttpException(500, e.getMessage());
                return null;
            }
            if (!response.isSuccessful()) {
                JSONObject error;
                try {
                    error = new JSONObject(respJson);
                } catch (JSONException e) {
                    this.exception = new HttpException(500, e.getMessage());
                    return null;
                }
                String errorMessage;
                try {
                    errorMessage = error.getString("error");
                } catch (JSONException e) {
                    this.exception = new HttpException(500, e.getMessage());
                    return null;
                }
                this.exception = new HttpException(response.code(), errorMessage);
                return null;
            }
            return respJson;
        }
    }
}
