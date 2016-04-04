package com.noveo.android.internship.ridetogether.app.model.rest;

import com.google.gson.Gson;

public enum GsonSingleton {
    INSTANCE;

    private final Gson GSON;

    GsonSingleton() {
        GSON = new Gson();
    }

    public static GsonSingleton getInstance() {
        return INSTANCE;
    }

    public Gson getGSON() {
        return GSON;
    }
}
