package com.noveo.android.internship.ridetogether.app.view.activity;


import com.noveo.android.internship.ridetogether.app.model.response.event.Event;

import java.util.List;

public interface EventListMvpView extends MvpView {
    void showEvents(List<Event> events);
}
