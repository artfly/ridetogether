package com.noveo.android.internship.ridetogether.app.view.activity;


import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;

public interface EventMvpView extends MvpView {
    void showRoute(Route route);

    void showEvent(Event event);
}
