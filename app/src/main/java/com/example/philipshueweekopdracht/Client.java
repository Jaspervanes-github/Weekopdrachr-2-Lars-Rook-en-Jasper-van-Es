package com.example.philipshueweekopdracht;

import org.json.JSONArray;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Client {

    public OkHttpClient client;
    private String username;
    private String ipAddress;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public Client() {
        this.client = new OkHttpClient();
        this.ipAddress = getBridgeIpAddress();
        this.username = createUsername();
    }

    private String getBridgeIpAddress() {
        //TODO: method for ipadress
        return "localhost";
    }

    private String createUsername() {
        //method to create username
        String username = createResponse(
                new Request.Builder()
                        .url("https://" + this.ipAddress + "/api")
                        .post(RequestBody.create("{\"devicetype\":\"my_hue_app#laptop Jasper\"}", JSON))
                        .build());
        return username;
    }

    private Request createGetRequest(String url) {
        Request request = new Request.Builder().url("https://" + this.ipAddress + "/api/" + this.username + url).build();
        return request;
    }

    private Request createPostRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url("https://" + this.ipAddress + "/api/" + this.username + url).post(requestBody).build();
        return request;
    }

    private String createResponse(Request request) {
        String responseString = "";
        try {
            Response response = this.client.newCall(request).execute();
            responseString = response.body().string();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return responseString;
    }

    public void turnLightOn(int id) {
        createResponse(createPostRequest("/lights/" + id + "/state", "{\"on\":true}"));
    }

    public void turnLightOff(int id) {
        createResponse(createPostRequest("/lights/" + id + "/state", "{\"on\":false}"));
    }
}
