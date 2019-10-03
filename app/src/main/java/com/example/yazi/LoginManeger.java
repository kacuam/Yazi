package com.example.yazi;

import android.content.Context;

public class LoginManeger {

    private Context context;
    private static LoginManeger maneger;

    private LoginManeger(Context context){
        this.context = context;
    }

    public static LoginManeger getInstance(Context context) {
        if (maneger == null) {
            maneger = new LoginManeger(context);
        }
        return maneger;
    }
}
