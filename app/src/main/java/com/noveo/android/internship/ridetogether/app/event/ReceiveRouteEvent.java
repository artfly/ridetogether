package com.noveo.android.internship.ridetogether.app.event;

import com.noveo.android.internship.ridetogether.app.rest.model.route.Route;
import org.json.JSONObject;

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
