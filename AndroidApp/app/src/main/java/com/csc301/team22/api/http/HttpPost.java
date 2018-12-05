package com.csc301.team22.api.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpPost extends BaseHttpRequest<String, Void, String> {

    public HttpPost(OkHttpClient client, String url, String email, String password) {
        super(client, url, email, password);
    }

    @Override
    protected String doInBackground(String... postJson) {
        RequestBody body = RequestBody.create(JSON, postJson[0]);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);
        authHeader().map(value -> requestBuilder.header("Authorization", value));
        Request request = requestBuilder.build();
        return super.makeRequest(request);
    }
}
