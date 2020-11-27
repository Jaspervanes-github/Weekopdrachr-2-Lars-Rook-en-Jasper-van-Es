package com.example.philipshueweekopdracht;

import android.graphics.Color;
import android.util.Log;

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
    private boolean isConnected;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public Client() {
        this.client = new OkHttpClient();
        this.port = 8000;
        this.isConnected = false;
    }

    private void Connect() {
        this.ipAddress = getBridgeIpAddress();
        this.username = createUsername();
        this.isConnected = true;
    }

    private String getBridgeIpAddress() {
        //Ipaddress of the emulator
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

    public void turnLampOn(int id) {
        if (this.isConnected)
            client.newCall(createPutRequest("/lights/" + id + "/state", "{\"on\":true}")).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FAILURE", "In OnFailure() in turnLampOn()");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        JSONArray responseArray = new JSONArray(response.body().string());

                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject responseObject = responseArray.getJSONObject(i);
                            if (responseObject.has("success")) {
                                Data.getInstance().getAllLamps().get(id).setPower(true);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response turnLampOn()");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void turnLampOff(int id) {
        if (this.isConnected)
            client.newCall(createPutRequest("/lights/" + id + "/state", "{\"on\":false}")).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FAILURE", "In OnFailure() in turnLampOff()");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        JSONArray responseArray = new JSONArray(response.body().string());

                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject responseObject = responseArray.getJSONObject(i);
                            if (responseObject.has("success")) {
                                //DELETELAMP
                                Data.getInstance().getAllLamps().get(id).setPower(false);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response turnLampOff()");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void deleteLamp(int id) {
        if (this.isConnected)
            client.newCall(createDeleteRequest("/lights/" + id)).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FAILURE", "In OnFailure() in deleteLamp()");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        JSONArray responseArray = new JSONArray(response.body().string());

                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject responseObject = responseArray.getJSONObject(i);
                            if (responseObject.has("success")) {
                                Data.getInstance().deleteLamp(id);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response deleteLamp()");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void setLampHue(int id, int hue) {
        if (this.isConnected)
            client.newCall(createPutRequest("/lights/" + id + "/state", "{\"bri\":" + hue + "}")).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FAILURE", "In OnFailure() in setLampHue()");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        JSONArray responseArray = new JSONArray(response.body().string());

                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject responseObject = responseArray.getJSONObject(i);
                            if (responseObject.has("success")) {
                                Data.getInstance().getAllLamps().get(id).setHueValue(hue);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response setLampHue()");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void setLampSaturation(int id, int saturation) {
        if (this.isConnected)
            client.newCall(createPutRequest("/lights/" + id + "/state", "{\"sat\":" + saturation + "}")).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FAILURE", "In OnFailure() in setLampSaturation()");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        JSONArray responseArray = new JSONArray(response.body().string());

                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject responseObject = responseArray.getJSONObject(i);
                            if (responseObject.has("success")) {
                                Data.getInstance().getAllLamps().get(id).setSatValue(saturation);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response setLampSaturation()");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void setLampBrightness(int id, int brightness) {
        if (this.isConnected)
            client.newCall(createPutRequest("/lights/" + id + "/state", "{\"bri\":" + brightness + "}")).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FAILURE", "In OnFailure() in setLampBrightness()");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        JSONArray responseArray = new JSONArray(response.body().string());

                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject responseObject = responseArray.getJSONObject(i);
                            if (responseObject.has("success")) {
                                Data.getInstance().getAllLamps().get(id).setBriValue(brightness);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response setLampBrightness()");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void setLampColor(int id, int r, int g, int b) {
        if (this.isConnected) {
            float[] hsb = {};
            Color.RGBToHSV(r, g, b, hsb);

            client.newCall(createPutRequest("/lights/" + id + "/state", "{\n" +
                    "    \"hue\":" + hsb[0] + ",\n" +
                    "    \"sat\":" + hsb[1] + ",\n" +
                    "    \"bri\":" + hsb[2] + "\n" +
                    "}")).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FAILURE", "In OnFailure() in setLampName()");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        JSONArray responseArray = new JSONArray(response.body().string());
                        boolean changeColorSuccesfull = true;
                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject responseObject = responseArray.getJSONObject(i);
                            if (!responseObject.has("success")) {
                                changeColorSuccesfull = false;
                                break;
                            }
                        }
                        if (changeColorSuccesfull) {
                            Data.getInstance().getAllLamps().get(id).setHueValue(responseArray.getJSONObject(0).getInt("/lights/" + id + "/state/hue"));
                            Data.getInstance().getAllLamps().get(id).setSatValue(responseArray.getJSONObject(0).getInt("/lights/" + id + "/state/sat"));
                            Data.getInstance().getAllLamps().get(id).setBriValue(responseArray.getJSONObject(0).getInt("/lights/" + id + "/state/bri"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setLampName(int id, String name) {
        if (this.isConnected)
            client.newCall(createPutRequest("/lights/" + id, "{\"name\":\"" + name + "\"}")).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FAILURE", "In OnFailure() in setLampName()");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        JSONArray responseArray = new JSONArray(response.body().string());

                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject responseObject = responseArray.getJSONObject(i);
                            if (responseObject.has("success")) {
                                Data.getInstance().getAllLamps().get(id).setNameLamp(name);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response setLampName()");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void getAllLamps() {
        if (this.isConnected)
            client.newCall(createGetRequest("/lights")).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("FAILURE", "In OnFailure() in getAllLamps()");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            ArrayList<Lamp> lampList = new ArrayList<>();

                            JSONObject responseArray = new JSONObject(response.body().string());
                            Iterator<String> keys = responseArray.keys();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject responseObject = responseArray.getJSONObject(key);

                                int color = Color.HSVToColor(new float[]{
                                        (float) ((responseObject.getJSONObject("state").getInt("hue") / 65535.0) * 360.0),
                                        (float) (responseObject.getJSONObject("state").getInt("sat") / 255.0),
                                        (float) (responseObject.getJSONObject("state").getInt("bri") / 255.0)});

                                lampList.add(new Lamp(
                                        key,
                                        responseObject.getString("name"), responseObject.getJSONObject("state").getBoolean("on"),
                                        Color.red(color),
                                        Color.green(color),
                                        Color.blue(color)));
                            }
                            Data.getInstance().setAllLamps(lampList);
                        } else {
                            //ERROR
                            Log.d("ERROR", "Error in response getAllLamps()");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

}