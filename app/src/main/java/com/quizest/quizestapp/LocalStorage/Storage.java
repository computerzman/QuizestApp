package com.quizest.quizestapp.LocalStorage;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {

//    all global field constants for the local stoarage
    private static final boolean isSoundEnabled = true;
    private static final boolean LOGIN_SATE = false;
    private static final int USERID = 1;
    private static final String ACCESS_TOKEN = null;
    private static final String USERNAME = null;
    private static final String FIREBASE_TOKEN = null ;
    private static final String ACCESS_TYPE = "Bearer";
    private static final String CATEGORY_RESPONSE = null;
    private static final String LEADERBOARD_RESPONSE = null;
    private Context context;

    public Storage(Context context) {
        this.context = context;
    }


    private SharedPreferences.Editor getPreferencesEditor() {
        return getsharedPreferences().edit();
    }

    private SharedPreferences getsharedPreferences() {

        return context.getSharedPreferences("QuizApp", Context.MODE_PRIVATE);
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

    public void saveUserName(String name){
        getPreferencesEditor().putString("name", name).commit();
    }

    public String getUserName(){
        return getsharedPreferences().getString("name" , USERNAME);
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

    public String getFirebaseToken() {

        return getsharedPreferences().getString("fire_token", FIREBASE_TOKEN);
    }

    public void SaveFirebaseToken(String token) {

        getPreferencesEditor().putString("fire_token", token).commit();

    }

    public void saveUserId(int i){
        getPreferencesEditor().putInt("id", i).commit();
    }

    public int getUserId(){
        return getsharedPreferences().getInt("id", USERID);
    }

    public void saveCategoryResponse(String response){
        getPreferencesEditor().putString("category_response", response).commit();
    }

    public String getCategoryResponse(){
       return getsharedPreferences().getString("category_response", CATEGORY_RESPONSE);

    }

    public void saveLeaderBoardResponse(String response){
        getPreferencesEditor().putString("category_response", response).commit();
    }

    public String getLeaderBoardResponse(){
        return getsharedPreferences().getString("category_response", LEADERBOARD_RESPONSE);

    }


}
