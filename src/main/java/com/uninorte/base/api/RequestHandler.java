package com.uninorte.base.api;

import okhttp3.*;

import java.io.IOException;

public class RequestHandler {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client;

    private final String baseUrl;

    public RequestHandler(String baseUrl) {
        this.baseUrl = baseUrl;

        client  = new OkHttpClient();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public RequestResponse postRequest(String endpoint, String json) throws IOException {
        String targetUrl = this.baseUrl + endpoint;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(targetUrl)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return new RequestResponse(response);
    }

    public String getRequest(String endpoint) throws IOException {
        String targetUrl = this.baseUrl + endpoint;
        Request request = new Request.Builder()
                .url(targetUrl)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    static class RequestResponse {
        protected int statusCode;
        protected String bodyResponse;

        RequestResponse(Response response) throws IOException {
            this.statusCode = response.code();
            this.bodyResponse = response.body().string();
        }
    }
}
