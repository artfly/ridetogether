package com.noveo.android.internship.ridetogether.app.model.event;

import com.noveo.android.internship.ridetogether.app.model.response.event.Event;

import java.util.List;

public class ReceiveRideEvents {
    private List<Event> events;

    public ReceiveRideEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
