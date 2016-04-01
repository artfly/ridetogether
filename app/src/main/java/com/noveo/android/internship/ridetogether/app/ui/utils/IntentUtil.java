package com.noveo.android.internship.ridetogether.app.ui.utils;

import android.content.Context;
import android.content.Intent;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.Events;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.ui.activity.EventActivity;
import com.noveo.android.internship.ridetogether.app.ui.activity.MainActivity;
import com.noveo.android.internship.ridetogether.app.ui.activity.RouteActivity;
import org.parceler.Parcels;

import java.util.List;

public class IntentUtil {
    public static final String EVENTS_TAG = "EVENTS_TAG";
    public static final String EVENT_TAG = "EVENT_TAG";
    public static final String ROUTE_TAG = "ROUTE_TAG";

    public static Intent createIntent(final Context context, final List<Event> events) {
        final Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EVENTS_TAG, Parcels.wrap(new Events(events)));
        return intent;
    }

    public static Intent createIntent(final Context context, final Event event) {
        final Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(EVENT_TAG, Parcels.wrap(event));
        return intent;
    }

    public static Intent createIntent(final Context context, final Route route) {
        final Intent intent = new Intent(context, RouteActivity.class);
        intent.putExtra(ROUTE_TAG, Parcels.wrap(route));
        return intent;
    }

    public static Route getRoute(final Intent intent) {
        return Parcels.unwrap(intent.getParcelableExtra(ROUTE_TAG));
    }

    public static Event getEvent(final Intent intent) {
        return Parcels.unwrap(intent.getParcelableExtra(EVENT_TAG));
    }

    public static List<Event> getEvents(final Intent intent) {
        return ((Events)Parcels.unwrap(intent.getParcelableExtra(EVENTS_TAG))).getEvents();
    }
}
