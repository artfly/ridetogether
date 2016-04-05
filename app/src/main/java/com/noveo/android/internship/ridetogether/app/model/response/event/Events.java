package com.noveo.android.internship.ridetogether.app.model.response.event;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Events {
    @SerializedName("events")
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
