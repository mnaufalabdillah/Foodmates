package com.rpl6.foodmates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String USER = "USER";
    private static final String CHEF = "CHEF";
    private static final String LOGIN = "IS_LOGIN";
    public static final String EMAIL = "EMAIL";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        sharedPreferences = context.getSharedPreferences(USER, PRIVATE_MODE);
        sharedPreferences = context.getSharedPreferences(CHEF, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String email){
        editor.putBoolean(LOGIN, true);
        editor.putBoolean(USER, true);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    //chef
    public void createSessionChef(String email){
        editor.putBoolean(LOGIN, true);
        editor.putBoolean(CHEF, true);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public boolean isLoginChef(){
        return sharedPreferences.getBoolean(LOGIN, false)  && sharedPreferences.getBoolean(CHEF, false);
    }

    public boolean isLoginUser(){
        return sharedPreferences.getBoolean(LOGIN, false)  && sharedPreferences.getBoolean(USER, false);
    }

    public void checkLogin(){
        if(!this.isLoginUser()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((HomeActivity) context).finish();
        }
    }

    //chef
    public void checkLoginChef(){
        if(!this.isLoginChef()){
            Intent ii = new Intent(context, LoginActivity.class);
            context.startActivity(ii);
            ((HomeActivityChef) context).finish();
        }
    }

    //chef
    public HashMap<String, String> loadchef(){
        HashMap<String, String> chef = new HashMap<>();
        chef.put(EMAIL, sharedPreferences.getString(EMAIL, null));

        return chef;
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}