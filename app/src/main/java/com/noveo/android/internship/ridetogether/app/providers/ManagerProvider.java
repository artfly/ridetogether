package com.noveo.android.internship.ridetogether.app.providers;

import android.content.Context;
import com.noveo.android.internship.ridetogether.app.controllers.EventManager;
import com.noveo.android.internship.ridetogether.app.controllers.Manager;

public class ManagerProvider {
    private static final Manager MANAGER = new EventManager();

    private ManagerProvider() {
    }

    public static Manager getInstance(Context context) {
        MANAGER.setContext(context);
        return MANAGER;
    }
}
