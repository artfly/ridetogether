package com.noveo.android.internship.ridetogether.app.presentation.main.home;

import android.content.Context;
import android.os.Bundle;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseViewActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.events.EventListView;
import com.noveo.android.internship.ridetogether.app.presentation.main.events.EventsPresenter;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;

import java.util.List;

public class SplashScreenActivity extends BaseViewActivity implements EventListView {
    private EventsPresenter presenter = new EventsPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
        setContentView(R.layout.splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadEventList();
    }

    @Override
    public void showEvents(List<Event> events) {
        startActivity(IntentUtil.createIntent(this, events));
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void attachPresenter() {
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        presenter.detachView();
    }
}
