package com.adel.androidbro.irannews.setting;

import android.content.Context;
import android.content.SharedPreferences;


public class SettingSharedPrefManager {
    private SharedPreferences sharedPreferences;
    private static final String EXTRA_KEY_LANG = "lang";

    private static SettingSharedPrefManager instance;

    public static SettingSharedPrefManager getInstance(Context context) {
        if (instance == null)
            instance = new SettingSharedPrefManager(context);
        return instance;
    }

    private SettingSharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    public void saveDefaultLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EXTRA_KEY_LANG, language);
        editor.commit();
    }

    public String getDefaultLanguage() {
        return sharedPreferences.getString(EXTRA_KEY_LANG, "en");
    }
}
