package com.csc301.team22.api.http;


import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpGetDelete extends BaseHttpRequest<Map<String, String>, Void, String> {
    private String method;

    HttpGetDelete(OkHttpClient client, String url, String email, String password, boolean isGet) {
        super(client, url, email, password);
        method = isGet ? "GET" : "DELETE";
    }

    @Override
    protected String doInBackground(Map<String, String>[] queryParams) {
        HttpUrl.Builder urlBuilder;
        try {
            urlBuilder = HttpUrl.parse(url).newBuilder();
        } catch (NullPointerException e) {
            super.exception = new HttpException(400, "Bad URL");
            return null;
        }
        for (Map.Entry<String, String> entry : queryParams[0].entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        Request.Builder requestBuilder = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Content-Type", "application/json")
                .method(method, null);
        authHeader().map(value -> requestBuilder.addHeader("Authorization", value));
        return super.makeRequest(requestBuilder.build());
    }
}
