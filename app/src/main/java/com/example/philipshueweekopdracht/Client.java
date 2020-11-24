package com.example.philipshueweekopdracht;

import android.graphics.Color;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Client {

    private OkHttpClient client;
    private String username;
    private String ipAddress;
    private int port;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public Client() {
        this.client = new OkHttpClient();
        this.ipAddress = getBridgeIpAddress();
        this.port = 8000;
        this.username = createUsername();
    }

    private String getBridgeIpAddress() {
        return "10.0.2.2";
    }

    private String createUsername() {
        //method to create username
        String responseString = createResponse(
                new Request.Builder()
                        .url("http://" + this.ipAddress + ":" + this.port + "/api")
                        .post(RequestBody.create("{\"devicetype\":\"my_hue_app#laptop Jasper\"}", JSON))
                        .build());

        String username = "";
        try {
            JSONArray response = new JSONArray(responseString);
            username = response.
                    getJSONObject(0).
                    getJSONObject("success").
                    getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
            //TODO: handle error data
        }

        return username;
    }

    private Request createGetRequest(String url) {
        Request request = new Request.Builder().url("http://" + this.ipAddress + ":" + this.port + "/api/" + this.username + url).build();
        return request;
    }

    private Request createPostRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url("http://" + this.ipAddress + ":" + this.port + "/api/" + this.username + url).post(requestBody).build();
        return request;
    }

    private Request createPutRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url("http://" + this.ipAddress + ":" + this.port + "/api/" + this.username + url).put(requestBody).build();
        return request;
    }

    private Request createDeleteRequest(String url) {
        Request request = new Request.Builder().url("http://" + this.ipAddress + ":" + this.port + "/api/" + this.username + url).delete().build();
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

    private void createAsyncResponse(Request request) {
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //TODO: return response.body().string();
            }
        });
    }

    public boolean turnLampOn(int id) {
        String response = createResponse(createPutRequest("/lights/" + id + "/state", "{\"on\":true}"));
        return response.contains("success");
    }

    public boolean turnLampOff(int id) {
        String response = createResponse(createPutRequest("/lights/" + id + "/state", "{\"on\":false}"));
        return response.contains("success");
    }

    public boolean deleteLamp(int id) {
        String response = createResponse(createDeleteRequest("/lights/" + id));
        return response.contains("success");
    }

    public boolean setLampBrightness(int id, int brightness) {
        String response = createResponse(createPutRequest("/lights/" + id + "/state", "{\"bri\":" + brightness + "}"));
        return response.contains("success");
    }

    public boolean setLampSaturation(int id, int saturation) {
        String response = createResponse(createPutRequest("/lights/" + id + "/state", "{\"sat\":" + saturation + "}"));
        return response.contains("success");
    }

    public boolean setLampColor(int id, int r, int g, int b) {
        float[] hsb = {};
        Color.RGBToHSV(r, g, b, hsb);

        String response = createResponse(createPutRequest("/lights/" + id + "/state", "{\n" +
                "    \"hue\":" + hsb[0] + ",\n" +
                "    \"sat\":" + hsb[1] + ",\n" +
                "    \"bri\":" + hsb[2] + "\n" +
                "}"));
        return response.contains("success");
    }

    public boolean setLampName(int id, String name) {
        String response = createResponse(createPutRequest("/lights/" + id, "{\"name\":\"" + name + "\"}"));
        return response.contains("success");
    }

    public void getAllLamps() {
        ArrayList<Lamp> lampList = new ArrayList<>();

        String responseString = createResponse(createGetRequest("/lights"));
        try {
            JSONObject response = new JSONObject(responseString);
            Iterator<String> keys = response.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                JSONObject object = response.getJSONObject(key);

                int color = Color.HSVToColor(new float[]{
                        (float)((object.getJSONObject("state").getInt("hue")/65535.0)*360.0),
                        (float)(object.getJSONObject("state").getInt("sat")/255.0),
                        (float)(object.getJSONObject("state").getInt("bri")/255.0)});

                    lampList.add(new Lamp(
                            key,
                            object.getString("name"),object.getJSONObject("state").getBoolean("on"),
                            Color.red(color),
                            Color.green(color),
                            Color.blue(color)));
            }
            Data.getInstance().setAllLamps(lampList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
