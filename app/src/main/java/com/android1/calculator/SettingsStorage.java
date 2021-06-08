package com.android1.calculator;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.material.switchmaterial.SwitchMaterial;

import static android.content.Context.MODE_PRIVATE;

public class SettingsStorage {

    SharedPreferences sPreferences;
    static final String SWITCH_STATE = "SWITCH_STATE";

    public SettingsStorage(Context context) {
        sPreferences = context.getSharedPreferences("switch_state", MODE_PRIVATE);
    }

    public boolean getSwitchState() {

        return sPreferences.getBoolean(SWITCH_STATE, false);

    }

    public void setSwitchState(boolean state) {

        sPreferences.edit()
                .putBoolean(SWITCH_STATE, state)
                .apply();
    }
}
