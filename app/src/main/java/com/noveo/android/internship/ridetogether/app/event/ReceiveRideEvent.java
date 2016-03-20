package com.noveo.android.internship.ridetogether.app.event;

import com.noveo.android.internship.ridetogether.app.rest.model.Event;

/**
 * Created by arty on 21.03.16.
 */
public class ReceiveRideEvent {
    private Event rideEvent;

    public ReceiveRideEvent(Event rideEvent) {
        this.rideEvent = rideEvent;
    }

    public Event getRideEvent() {
        return rideEvent;
    }
}
