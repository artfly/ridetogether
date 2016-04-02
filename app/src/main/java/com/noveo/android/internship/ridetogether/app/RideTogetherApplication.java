package com.noveo.android.internship.ridetogether.app;

import android.app.Application;
import com.noveo.android.internship.ridetogether.app.providers.BusProvider;
import com.noveo.android.internship.ridetogether.app.controllers.Manager;
import com.noveo.android.internship.ridetogether.app.providers.ManagerProvider;
import com.squareup.otto.Bus;

public class RideTogetherApplication extends Application {

    private Manager eventManager = ManagerProvider.getInstance(null);
    private Bus bus = BusProvider.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        bus.register(eventManager);
        bus.register(this);
    }
}
