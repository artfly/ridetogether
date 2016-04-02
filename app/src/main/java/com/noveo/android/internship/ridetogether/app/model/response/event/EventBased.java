package com.noveo.android.internship.ridetogether.app.model.response.event;

public interface EventBased {
    EventViewType getViewType();

    enum EventViewType {
        EVENT, SECTION, USER, ERROR
    }
}
