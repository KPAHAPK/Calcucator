package com.android1.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    ThemeStorage themeStorage;
    SettingsStorage settingsStorage;
    SwitchMaterial switchTheme;
    MainActivity mainActivity;
    private static final String KEY_RESULT = "KEY_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeStorage = new ThemeStorage(this);
        settingsStorage = new SettingsStorage(this);


        setTheme(themeStorage.getTheme().getResources());

        setContentView(R.layout.activity_settings);

        switchTheme = findViewById(R.id.switch_theme);
        switchTheme.setChecked(settingsStorage.getSwitchState());


        switchTheme.setOnClickListener(v -> {
            if (switchTheme.isChecked()) {
                themeStorage.setTheme(AppTheme.DARK);
                settingsStorage.setSwitchState(true);
            } else {
                themeStorage.setTheme(AppTheme.LIGHT);
                settingsStorage.setSwitchState(false);
            }
            Intent resultIntent = new Intent();
            resultIntent.putExtra(KEY_RESULT, switchTheme.isChecked());
            setResult(Activity.RESULT_OK, resultIntent);
            recreate();
        });
    }
}