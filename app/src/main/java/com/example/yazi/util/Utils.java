package com.example.yazi.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

    private Context context;
    private SharedPreferences sharedPreferences;

    private static final String ID = "sessionId",MESSAGE = "message";

    public Utils(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences("MY_CHAT",0);
    }

    public void storeSessionId(String sessionId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID, sessionId);
        editor.commit();
    }

    public String getSessionId() {
        return sharedPreferences.getString(ID, null);
    }

    public String getSendMessageJSON(String message) {
        String json = null;

        try {
            JSONObject jobj = new JSONObject();
            jobj.put("flag", MESSAGE);
            jobj.put("sessionId", getSessionId());
            jobj.put("message", message);

            json = jobj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
