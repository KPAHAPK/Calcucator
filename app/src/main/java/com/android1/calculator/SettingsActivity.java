package com.android1.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    public static ThemeStorage themeStorage;
    public static final String KEY_RESULT = "KEY_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        themeStorage = new ThemeStorage(this);
        setTheme(themeStorage.getTheme().getResources());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        findViewById(R.id.day_mode_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeStorage.setTheme(AppTheme.LIGHT);
                applyTheme();
            }
        });

        findViewById(R.id.night_mode_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeStorage.setTheme(AppTheme.DARK);
                applyTheme();
            }
        });
    }

    public void applyTheme() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_RESULT, themeStorage.getTheme().getResources());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}