package com.example.philipshueweekopdracht;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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


    private final Handler handler;
    private Random random;
    private Timer fadingTimer;
    private Timer discoTimer;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public Client() {
        this.client = new OkHttpClient();
        this.port = 8000;
        this.isConnected = false;

        this.handler = new Handler();
        this.random = new Random();
        this.fadingTimer = new Timer(false);
        this.discoTimer = new Timer(false);
    }

    public void Connect() {
        this.ipAddress = getBridgeIpAddress();
        Message.createLinkButtonDialog(this);
    }

    private String getBridgeIpAddress() {
        //Ipaddress of the emulator
        return "10.0.2.2";
    }

    public void createUsername() {
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
            Message.createLinkButtonDialog(this);
        }

        if (username != "") {
            this.username = username;
            this.isConnected = true;
        }
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
            client.newCall(createPutRequest("/lights/" + (id + 1) + "/state", "{\"on\":true}")).enqueue(new Callback() {
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

                                Message.createToastMessage(Data.getInstance().getContext().getString(R.string.turnLampOn,
                                        Data.getInstance().getAllLamps().get(id).getNameLamp()), Toast.LENGTH_SHORT);

                                Data.getInstance().getAllLamps().get(id).setPower(true);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response turnLampOn()");
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Data.getInstance().updateViewModelLampList();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void turnLampOff(int id) {
        if (this.isConnected)
            client.newCall(createPutRequest("/lights/" + (id + 1) + "/state", "{\"on\":false}")).enqueue(new Callback() {
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

                                Message.createToastMessage(Data.getInstance().getContext().getString(R.string.turnLampOff,
                                        Data.getInstance().getAllLamps().get(id).getNameLamp()), Toast.LENGTH_SHORT);

                                Data.getInstance().getAllLamps().get(id).setPower(false);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response turnLampOff()");
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Data.getInstance().updateViewModelLampList();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void setPowerOfAllLamps(boolean state) {
        for (int i = 0; i < Data.getInstance().getAllLamps().size(); i++) {
            if (state) {
                turnLampOn(i);
            } else {
                turnLampOff(i);
            }
        }
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
                                Message.createToastMessage(Data.getInstance().getContext().getString(R.string.deleteLamp,
                                        Data.getInstance().getAllLamps().get(id).getNameLamp()), Toast.LENGTH_SHORT);

                                Data.getInstance().deleteLamp(id);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response deleteLamp()");
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Data.getInstance().updateViewModelLampList();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void setLampHue(int id, int hue) {
        if (this.isConnected)
            client.newCall(createPutRequest("/lights/" + id + "/state", "{\"hue\":" + hue + "}")).enqueue(new Callback() {
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
                                Data.getInstance().getAllLamps().get(id - 1).setHueValue(hue);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response setLampHue()");
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Data.getInstance().updateViewModelLampList();
                            }
                        });
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
                                Data.getInstance().getAllLamps().get(id - 1).setSatValue(saturation);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response setLampSaturation()");
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Data.getInstance().updateViewModelLampList();
                            }
                        });
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
                                Data.getInstance().getAllLamps().get(id - 1).setBriValue(brightness);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response setLampBrightness()");
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Data.getInstance().updateViewModelLampList();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void setLampColor(int id, int r, int g, int b) {
        if (this.isConnected) {
            float[] hsb = calculateHSBColor(r, g, b);

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
                            Message.createToastMessage(Data.getInstance().getContext().getString(R.string.setLampColor,
                                    Data.getInstance().getAllLamps().get(id - 1).getNameLamp()), Toast.LENGTH_SHORT);

                            Data.getInstance().getAllLamps().get(id - 1).setHueValue(responseArray.getJSONObject(0).getInt("/lights/" + id + "/state/hue"));
                            Data.getInstance().getAllLamps().get(id - 1).setSatValue(responseArray.getJSONObject(0).getInt("/lights/" + id + "/state/sat"));
                            Data.getInstance().getAllLamps().get(id - 1).setBriValue(responseArray.getJSONObject(0).getInt("/lights/" + id + "/state/bri"));
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Data.getInstance().updateViewModelLampList();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private float[] calculateHSBColor(int red, int green, int blue) {
        float[] hsb = new float[3];
        Color.RGBToHSV(red, green, blue, hsb);

        System.out.println((hsb[0] / 360) * 65535 + "   " + hsb[1] * 255 + "  " + hsb[2] * 255);
        hsb[0] = (Math.round((hsb[0] / 360) * 65535));
        hsb[1] = (Math.round(hsb[1] * 255));
        hsb[2] = (Math.round(hsb[2] * 255));

        return hsb;
    }

    public void startFadingOfLamp(int id, int increaseHueAmount, Lamp lampSelected) {
        if (this.isConnected) {
            setLampSaturation(id, 254);
            setLampBrightness(id, 125);

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            int hue = Data.getInstance().getAllLamps().get(id-1).getHueValue() + increaseHueAmount;
                            if (hue >= 65535) {
                                hue = hue - 65535;
                            }
                            setLampHue(id, hue);
                            if (lampSelected.isFadingMode() && lampSelected.isPower()) {
                                fadingTimer.schedule(createFadingTimerTask(id, increaseHueAmount, lampSelected), lampSelected.getFadingSpeed());
                            } else {
                                stopFadingOfLamp();
                            }
                        }
                    });
                }
            };
            this.fadingTimer.schedule(timerTask, lampSelected.getFadingSpeed());
        }
    }

    private TimerTask createFadingTimerTask(int id, int increaseHueAmount, Lamp lampSelected) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int hue = Data.getInstance().getAllLamps().get(id).getHueValue() + increaseHueAmount;
                if (hue >= 65535) {
                    hue = hue - 65535;
                }
                setLampHue(id, Data.getInstance().getAllLamps().get(id-1).getHueValue() + increaseHueAmount);
                if (lampSelected.isFadingMode() && lampSelected.isPower()) {
                    fadingTimer.schedule(createFadingTimerTask(id, increaseHueAmount, lampSelected), lampSelected.getFadingSpeed());
                } else {
                    stopFadingOfLamp();
                }
            }
        };
        return timerTask;
    }

    public void stopFadingOfLamp() {
        if (this.isConnected && Data.getInstance().getLampSelected().isFadingMode()) {
            this.fadingTimer.purge();
            this.fadingTimer.cancel();
        }
    }

    public void startDiscoOfLamp(int id, Lamp lampSelected) {
        if (this.isConnected) {
            setLampSaturation(id, 254);
            setLampBrightness(id, 125);

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            setLampHue(id, random.nextInt(65535));
                            if (lampSelected.isDiscoMode() && lampSelected.isPower()) {
                                fadingTimer.schedule(createDiscoTimerTask(id, lampSelected), lampSelected.getDiscoSpeed());
                            } else {
                                stopDiscoOfLamp();
                            }
                        }
                    });
                }
            };
            this.discoTimer.schedule(timerTask, lampSelected.getDiscoSpeed());
        }
    }

    private TimerTask createDiscoTimerTask(int id, Lamp lampSelected) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setLampHue(id, random.nextInt(65535));
                if (lampSelected.isDiscoMode() && lampSelected.isPower()) {
                    fadingTimer.schedule(createDiscoTimerTask(id, lampSelected), lampSelected.getDiscoSpeed());
                } else {
                    stopDiscoOfLamp();
                }
            }
        };
        return timerTask;
    }

    public void stopDiscoOfLamp() {
        if (this.isConnected && Data.getInstance().getLampSelected().isDiscoMode()) {
            this.discoTimer.purge();
            this.discoTimer.cancel();
        }
    }

    public void setLampName(int id, String name) {
        String previousName = Data.getInstance().getAllLamps().get(id - 1).getNameLamp();
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
                                Message.createToastMessage(Data.getInstance().getContext().getString(R.string.setLampName, previousName,
                                        Data.getInstance().getAllLamps().get(id - 1).getNameLamp()), Toast.LENGTH_SHORT);

                                Data.getInstance().getAllLamps().get(id - 1).setNameLamp(name);
                                System.out.println(name);
                            } else {
                                //ERROR
                                Log.d("ERROR", "Error in response setLampName()");
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Data.getInstance().updateViewModelLampList();
                            }
                        });
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
                            Message.createToastMessage(Data.getInstance().getContext().getString(R.string.getAllLamps), Toast.LENGTH_SHORT);

                            Data.getInstance().setAllLamps(lampList);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Data.getInstance().updateViewModelLampList();
                                }
                            });

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
