package com.quizest.quizestapp.LocalStorage;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {

    private static final boolean isSoundEnabled = true;
    private static final boolean LOGIN_SATE = false;
    private static final String ACCESS_TOKEN = null;
    private static final String ACCESS_TYPE = "Bearer";
    private Context context;

    public Storage(Context context) {
        this.context = context;
    }


    private SharedPreferences.Editor getPreferencesEditor() {
        return getsharedPreferences().edit();
    }

    private SharedPreferences getsharedPreferences() {

        return context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
    }


    public void SaveLogInSate(boolean p) {

        getPreferencesEditor().putBoolean("logged_in", p).commit();

    }

    public boolean getLogInState() {

        return getsharedPreferences().getBoolean("logged_in", LOGIN_SATE);
    }

    public void SaveAccessToken(String token) {

        getPreferencesEditor().putString("token", token).commit();

    }

    public String getAccessToken() {

        return getAccessType() + " " + getsharedPreferences().getString("token", ACCESS_TOKEN);
    }

    public void SaveAccessType(String type) {

        getPreferencesEditor().putString("type", type).commit();

    }

    public String getAccessType() {

        return getsharedPreferences().getString("type", ACCESS_TYPE);
    }

    public void saveSoundState(boolean p) {
        getPreferencesEditor().putBoolean("sound", p).commit();
    }

    public boolean getSoundState() {
        return getsharedPreferences().getBoolean("sound", isSoundEnabled);
    }

}
