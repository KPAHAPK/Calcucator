package com.android1.calculator;

public enum  AppTheme {
    LIGHT(R.style.Calculator_Light, "LightTheme"),
    DARK(R.style.Calculator_Dark, "DarkTheme");

    AppTheme(int resources, String key) {
        this.resources = resources;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    private int resources;

    private String key;

    public int getResources() {
        return resources;
    }
}
