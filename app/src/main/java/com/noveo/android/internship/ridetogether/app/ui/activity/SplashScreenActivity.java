package com.noveo.android.internship.ridetogether.app.ui.activity;

import android.os.Bundle;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.controllers.Manager;
import com.noveo.android.internship.ridetogether.app.model.event.ReceiveRideEvents;
import com.noveo.android.internship.ridetogether.app.providers.ManagerProvider;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;
import com.squareup.otto.Subscribe;

public class SplashScreenActivity extends BaseActivity {
    private Manager manager = ManagerProvider.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.getEvents();
    }

    @Subscribe
    public void onReceiveEvents(ReceiveRideEvents event) {
        startActivity(IntentUtil.createIntent(this, event.getEvents()));
    }
}
