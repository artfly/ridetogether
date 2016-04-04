package com.noveo.android.internship.ridetogether.app;

import android.app.Application;
import com.noveo.android.internship.ridetogether.app.view.bus.BusProvider;
import com.squareup.otto.Bus;

public class RideTogetherApplication extends Application {
    private Bus bus = BusProvider.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();
        bus.register(this);
    }
}
