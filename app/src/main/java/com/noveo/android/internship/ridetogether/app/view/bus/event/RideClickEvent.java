package com.noveo.android.internship.ridetogether.app.view.bus.event;


import com.noveo.android.internship.ridetogether.app.model.response.event.Event;

public class RideClickEvent {
    private Event event;

    public RideClickEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
