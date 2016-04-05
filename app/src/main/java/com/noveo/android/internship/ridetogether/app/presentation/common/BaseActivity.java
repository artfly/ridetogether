package com.noveo.android.internship.ridetogether.app.presentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.view.bus.BusProvider;
import com.squareup.otto.Bus;

public abstract class BaseActivity extends AppCompatActivity {
    private Bus bus = BusProvider.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
