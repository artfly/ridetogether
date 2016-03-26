package com.noveo.android.internship.ridetogether.app.model.event;

import com.noveo.android.internship.ridetogether.app.model.response.route.Route;

/**
 * Created by arty on 23.03.16.
 */
public class ReceiveRouteEvent {
    private Route route;

    public ReceiveRouteEvent(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }
}
