package com.noveo.android.internship.ridetogether.app;

import android.app.Application;
import com.noveo.android.internship.ridetogether.app.model.service.BusProvider;
import com.noveo.android.internship.ridetogether.app.model.service.EventManager;
import com.noveo.android.internship.ridetogether.app.model.service.Manager;
import com.noveo.android.internship.ridetogether.app.model.service.ManagerProvider;
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
