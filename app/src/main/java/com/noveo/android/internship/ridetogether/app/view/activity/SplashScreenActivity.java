package com.noveo.android.internship.ridetogether.app.view.activity;

import android.os.Bundle;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.presenter.EventListPresenter;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;

import java.util.List;

public class SplashScreenActivity extends BaseMvpActivity implements EventListMvpView {
    private EventListPresenter presenter = new EventListPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void attachPresenter() {
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        presenter.detachView();
    }
}
