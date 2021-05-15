package com.example.reni.Slime.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


public class PreferenceUtils {
    public PreferenceUtils(){

    }

    public static boolean saveLogin(int ID, Context context){
        SharedPreferences sharedPreferencesLogin = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editorLogin = sharedPreferencesLogin.edit();

        editorLogin.putInt(Constant.KEY_ID, ID);
        editorLogin.apply();
        editorLogin.commit();

        return true;
    }

    public static int getLoginUser(Context context){
        SharedPreferences sharedPreferencesLogin = PreferenceManager.getDefaultSharedPreferences(context);

        Log.i("SHAREDPREFERENCES", sharedPreferencesLogin.getInt(Constant.KEY_ID, 0) + "");
        return sharedPreferencesLogin.getInt(Constant.KEY_ID, 0);
    }
}
