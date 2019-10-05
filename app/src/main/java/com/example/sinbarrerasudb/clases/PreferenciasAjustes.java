package com.example.sinbarrerasudb.clases;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferenciasAjustes {
    private String PREFS_KEY = "mispreferencias";


    public void SavePreferenceSwitchOnline(Context context, boolean valor) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean("estado_switch_Online", valor);
        editor.commit();
    }

    public boolean getPreferenceSwitchOnline(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return  preferences.getBoolean("estado_switch_Online", false);
    }

    public void SaveLevel1FirstTime(Context context, boolean valor)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean("primera_vez_nivel1", valor);
        editor.commit();
    }

    public void SaveLevel2FirstTime(Context context, boolean valor)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean("primera_vez_nivel2", valor);
        editor.commit();
    }

    public void SaveLevel3FirstTime(Context context, boolean valor)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean("primera_vez_nivel3", valor);
        editor.commit();
    }


    public boolean getStatusLevel1(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return  preferences.getBoolean("primera_vez_nivel1", false);
    }

    public boolean getStatusLevel2(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return  preferences.getBoolean("primera_vez_nivel2", false);
    }

    public boolean getStatusLevel3(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return  preferences.getBoolean("primera_vez_nivel3", false);
    }


}
