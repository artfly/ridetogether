package com.noveo.android.internship.ridetogether.app.presentation.main.event;


import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseView;

public interface EventView extends BaseView {
    void showRoute(Route route);

    void showEvent(Event event);
}
