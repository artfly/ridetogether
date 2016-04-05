package com.noveo.android.internship.ridetogether.app.utils;

import android.content.Context;
import android.content.Intent;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.Events;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.presentation.main.event.EventActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.events.EventsActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.route.RouteActivity;
import org.parceler.Parcels;

import java.util.List;

public final class IntentUtil {
    public static final String EVENTS_TAG = "EVENTS_TAG";
    public static final String EVENT_TAG = "EVENT_TAG";
    public static final String ROUTE_TAG = "ROUTE_TAG";
    public static final String ROUTE_ID_TAG = "EVENT_ID_TAG";

    private IntentUtil() {
    }

    public static Intent createIntent(final Context context, final List<Event> events) {
        final Intent intent = new Intent(context, EventsActivity.class);
        intent.putExtra(EVENTS_TAG, Parcels.wrap(new Events(events)));
        return intent;
    }

    public static Intent createIntent(final Context context, final Event event) {
        final Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(EVENT_TAG, Parcels.wrap(event));
        return intent;
    }

    public static Intent createIntent(final Context context, final Route route, final int routeId) {
        final Intent intent = new Intent(context, RouteActivity.class);
        intent.putExtra(ROUTE_TAG, Parcels.wrap(route));
        intent.putExtra(ROUTE_ID_TAG, routeId);
        return intent;
    }

    public static Route getRoute(final Intent intent) {
        return Parcels.unwrap(intent.getParcelableExtra(ROUTE_TAG));
    }

    public static Event getEvent(final Intent intent) {
        return Parcels.unwrap(intent.getParcelableExtra(EVENT_TAG));
    }

    public static List<Event> getEvents(final Intent intent) {
        return ((Events) Parcels.unwrap(intent.getParcelableExtra(EVENTS_TAG))).getEvents();
    }

    public static int getRouteId(final Intent intent) {
        return intent.getIntExtra(ROUTE_ID_TAG, -1);
    }
}
