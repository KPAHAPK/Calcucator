package com.android1.calculator;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class ThemeStorage {

    static SharedPreferences sharedPref;
    static final String KEY = "KEY";

    public ThemeStorage(Context context) {
        sharedPref = context.getSharedPreferences("app_theme", MODE_PRIVATE);

    }

    public AppTheme getTheme() {

        String key = sharedPref.getString(KEY, AppTheme.LIGHT.getKey());

        for (AppTheme appTheme : AppTheme.values()) {
            if (appTheme.getKey().equals(key)) {
                return appTheme;
            }
        }
        throw new IllegalStateException("fasdf");

    }

    public void setTheme(AppTheme appTheme) {

        sharedPref.edit()
                .putString(KEY, appTheme.getKey())
                .apply();

    }
}

