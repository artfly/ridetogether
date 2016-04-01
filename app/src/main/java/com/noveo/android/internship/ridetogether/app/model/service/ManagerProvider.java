package com.noveo.android.internship.ridetogether.app.model.service;

import android.content.Context;

public class ManagerProvider {

    private static final Manager MANAGER = new EventManager();

    private  ManagerProvider () {
    }

    public static Manager getInstance(Context context) {
        MANAGER.setContext(context);
        return MANAGER;
    }
}
