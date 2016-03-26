package com.noveo.android.internship.ridetogether.app.model.event;


public class SubscribeEvent {
    private String action;

    public SubscribeEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
