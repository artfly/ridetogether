package com.noveo.android.internship.ridetogether.app.model.response.event;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Events {
    List<Event> events;

    public Events() {
    }

    public Events(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
