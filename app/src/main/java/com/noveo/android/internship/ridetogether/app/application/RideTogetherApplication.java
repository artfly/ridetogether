package com.noveo.android.internship.ridetogether.app.application;

import android.app.Application;
import com.noveo.android.internship.ridetogether.app.bus.BusProvider;
import com.noveo.android.internship.ridetogether.app.manager.EventManager;
import com.squareup.otto.Bus;

public class RideTogetherApplication extends Application {

    private EventManager eventManager;
    private Bus bus = BusProvider.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        eventManager = new EventManager(this, bus);
        bus.register(eventManager);
        bus.register(this);
    }
}
