package com.noveo.android.internship.ridetogether.app.ui.view;

import com.noveo.android.internship.ridetogether.app.model.response.event.EventBased;


public class Section implements EventBased {

    public String title;

    public Section(String title) {
        this.title = title;
    }

    @Override
    public EventViewType getViewType() {
        return EventViewType.SECTION;
    }
}
