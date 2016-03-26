package com.noveo.android.internship.ridetogether.app.model.event;

import com.noveo.android.internship.ridetogether.app.model.response.event.Event;


public class ReceiveRideEvent {
    private Event rideEvent;

    public ReceiveRideEvent(Event rideEvent) {
        this.rideEvent = rideEvent;
    }

    public Event getRideEvent() {
        return rideEvent;
    }
}
