package com.noveo.android.internship.ridetogether.app.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.model.service.BusProvider;
import com.squareup.otto.Bus;

public abstract class BaseActivity extends AppCompatActivity {
    private Bus bus = BusProvider.getInstance();

    @Override
    public void setContentView(int id) {
        super.setContentView(id);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
