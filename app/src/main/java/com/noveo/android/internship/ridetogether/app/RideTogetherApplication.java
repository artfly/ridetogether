package com.noveo.android.internship.ridetogether.app;

import android.app.Application;
import com.noveo.android.internship.ridetogether.app.model.service.BusProvider;
import com.noveo.android.internship.ridetogether.app.model.service.EventManager;
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
