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
                //TODO: handle data
                String responseString = response.body().string();

                try {
                    JSONArray responseData = new JSONArray(responseString);

                    //if it is a lampList
                    if (responseData.getJSONObject(0).has("1")) {
                        ArrayList<Lamp> lampList = new ArrayList<>();

                        try {
                            JSONObject responseObject = new JSONObject(responseString);
                            Iterator<String> keys = responseObject.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject object = responseObject.getJSONObject(key);

                                int color = Color.HSVToColor(new float[]{
                                        (float) ((object.getJSONObject("state").getInt("hue") / 65535.0) * 360.0),
                                        (float) (object.getJSONObject("state").getInt("sat") / 255.0),
                                        (float) (object.getJSONObject("state").getInt("bri") / 255.0)});

                                lampList.add(new Lamp(
                                        key,
                                        object.getString("name"), object.getJSONObject("state").getBoolean("on"),
                                        Color.red(color),
                                        Color.green(color),
                                        Color.blue(color)));
                            }
                            Data.getInstance().setAllLamps(lampList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //if it isn't a lampList
                    } else {

                        for (int i = 0; i < responseData.length(); i++) {
                            JSONObject object = responseData.getJSONObject(i);

                            //if the response is valid
                            if (object.has("success")) {
                                //TODO: handle data
                                handleData(object.getJSONObject("success"));
                            }

                            //if the response isn't valid
                            else {
                                //TODO: handle error responses
                                //throw new Exception();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleData(JSONObject responseObject) {
        Iterator<String> keys = responseObject.keys();
        String key = keys.next();

        int id = Integer.parseInt(key.substring(9,key.lastIndexOf("/",key.indexOf("state"))));

        try {
            if (key.contains("/state/on")) {
                Data.getInstance().getAllLamps().get(id).setPower(responseObject.getBoolean(key));
            }else if(key.contains("/state/bri")){
                Data.getInstance().getAllLamps().get(id).setBriValue(responseObject.getInt(key));
            }else if(key.contains("/state/sat")){
                Data.getInstance().getAllLamps().get(id).setSatValue(responseObject.getInt(key));
            }else if(key.contains("/name")){
                Data.getInstance().getAllLamps().get(id).setNameLamp(responseObject.getString(key));
            }else if(key.contains("/state/hue")){
                Data.getInstance().getAllLamps().get(id).setHueValue(responseObject.getInt(key));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void turnLampOn(int id) {
        createAsyncResponse(createPutRequest("/lights/" + id + "/state", "{\"on\":true}"));
    }

    public void turnLampOff(int id) {
        createAsyncResponse(createPutRequest("/lights/" + id + "/state", "{\"on\":false}"));
    }

    public void deleteLamp(int id) {
        createAsyncResponse(createDeleteRequest("/lights/" + id));
    }

    public void setLampHue(int id, int hue) {
        createAsyncResponse(createPutRequest("/lights/" + id + "/state", "{\"bri\":" + hue + "}"));
    }

    public void setLampSaturation(int id, int saturation) {
        createAsyncResponse(createPutRequest("/lights/" + id + "/state", "{\"sat\":" + saturation + "}"));
    }

    public void setLampBrightness(int id, int brightness) {
        createAsyncResponse(createPutRequest("/lights/" + id + "/state", "{\"bri\":" + brightness + "}"));
    }

    public void setLampColor(int id, int r, int g, int b) {
        float[] hsb = {};
        Color.RGBToHSV(r, g, b, hsb);

        createAsyncResponse(createPutRequest("/lights/" + id + "/state", "{\n" +
                "    \"hue\":" + hsb[0] + ",\n" +
                "    \"sat\":" + hsb[1] + ",\n" +
                "    \"bri\":" + hsb[2] + "\n" +
                "}"));
    }

    public void setLampName(int id, String name) {
        createAsyncResponse(createPutRequest("/lights/" + id, "{\"name\":\"" + name + "\"}"));
    }

    public void getAllLamps() {
        createAsyncResponse(createGetRequest("/lights"));
    }

}
