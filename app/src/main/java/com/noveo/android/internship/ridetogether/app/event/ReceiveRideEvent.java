package com.noveo.android.internship.ridetogether.app.event;

import com.noveo.android.internship.ridetogether.app.rest.model.Event;


public class ReceiveRideEvent {
    private Event rideEvent;

    public ReceiveRideEvent(Event rideEvent) {
        this.rideEvent = rideEvent;
    }

    public Event getRideEvent() {
        return rideEvent;
    }
}
