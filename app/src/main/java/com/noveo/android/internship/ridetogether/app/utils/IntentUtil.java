package com.noveo.android.internship.ridetogether.app.utils;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import com.google.android.gms.maps.model.LatLng;
import com.noveo.android.internship.ridetogether.app.model.request.RequestEvent;
import com.noveo.android.internship.ridetogether.app.model.request.RequestProperties;
import com.noveo.android.internship.ridetogether.app.model.request.RequestRoute;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.Events;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.presentation.main.event.EventActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.events.EventsActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.route.RouteActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.route_creation.RouteCreationActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.route_creation.RouteCreationMapActivity;
import org.parceler.Parcels;

import java.util.List;

public final class IntentUtil {
    private static final String EVENTS_TAG = "EVENTS_TAG";
    private static final String EVENT_TAG = "EVENT_TAG";
    private static final String ROUTE_TAG = "ROUTE_TAG";
    private static final String ROUTE_ID_TAG = "EVENT_ID_TAG";
    private static final String LONGITUDE_TAG = "LONGITUDE_TAG";
    private static final String LATITUDE_TAG = "LATITUDE_TAG";
    private static final String PROPERTIES_TAG = "PROPERTIES_TAG";

    private IntentUtil() {
    }

    public static Intent createIntent(final Context context, final List<Event> events, final Address address) {
        final Intent intent = new Intent(context, EventsActivity.class);
        intent.putExtra(EVENTS_TAG, Parcels.wrap(new Events(events)));
        intent.putExtra(LONGITUDE_TAG, address.getLongitude());
        intent.putExtra(LATITUDE_TAG, address.getLatitude());
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

    public static Intent createIntent(final Context context, final RequestEvent event) {
        final Intent intent = new Intent(context, RouteCreationActivity.class);
        intent.putExtra(EVENT_TAG, Parcels.wrap(event));
        return intent;
    }

//    public static Intent createIntent(final Context context, final LatLng latLng) {
//        final Intent intent = new Intent(context, RouteCreationMapActivity.class);
//        intent.putExtra(LONGITUDE_TAG, latLng.longitude);
//        intent.putExtra(LATITUDE_TAG, latLng.latitude);
//        return intent;
//    }

    public static Intent createIntent(final Context context, final RequestProperties properties,
                                      final RequestEvent event, final LatLng latLng) {
        final Intent intent = new Intent(context, RouteCreationMapActivity.class);
        intent.putExtra(PROPERTIES_TAG, Parcels.wrap(properties));
        intent.putExtra(EVENT_TAG, Parcels.wrap(event));
        intent.putExtra(LONGITUDE_TAG, latLng.longitude);
        intent.putExtra(LATITUDE_TAG, latLng.latitude);
        return intent;
    }

    public static Route getRoute(final Intent intent) {
        return Parcels.unwrap(intent.getParcelableExtra(ROUTE_TAG));
    }

    public static RequestEvent getRequestEvent(final Intent intent) {
        return Parcels.unwrap(intent.getParcelableExtra(EVENT_TAG));
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

    public static LatLng getCurrentPosition(final Intent intent) {
        return new LatLng(intent.getDoubleExtra(LATITUDE_TAG, 0.0), intent.getDoubleExtra(LONGITUDE_TAG, 0.0));
    }

    public static RequestProperties getProperties(final Intent intent) {
        return Parcels.unwrap(intent.getParcelableExtra(PROPERTIES_TAG));
    }
}
