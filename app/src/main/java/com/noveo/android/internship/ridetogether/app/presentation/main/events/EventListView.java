package com.noveo.android.internship.ridetogether.app.presentation.main.events;


import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseView;

import java.util.List;

public interface EventListView extends BaseView {
    void showEvents(List<Event> events);
}
