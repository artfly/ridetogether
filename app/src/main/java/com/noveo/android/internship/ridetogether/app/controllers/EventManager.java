package com.noveo.android.internship.ridetogether.app.controllers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.RideTogetherStub;
import com.noveo.android.internship.ridetogether.app.model.event.ReceiveCommentsEvent;
import com.noveo.android.internship.ridetogether.app.model.event.ReceiveRideEvent;
import com.noveo.android.internship.ridetogether.app.model.event.ReceiveRideEvents;
import com.noveo.android.internship.ridetogether.app.model.event.ReceiveRouteEvent;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.providers.BusProvider;
import com.noveo.android.internship.ridetogether.app.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.rest.service.RoutesService;
import com.squareup.otto.Bus;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;

public class EventManager implements Manager {
    private Context context;
    private Bus bus = BusProvider.getInstance();
    private EventsService eventsService;
    private RoutesService routesService;

    public EventManager() {
        RideTogetherClient client = RideTogetherClient.getInstance();
        this.eventsService = client.getEventsService();
        this.routesService = client.getRoutesService();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void subscribe(CharSequence action) {
        if (TextUtils.equals(action, context.getString(R.string.subscribe))) {
            Call<Event> subscribeCall = eventsService
                    .subscribeToEvent(RideTogetherStub.eventId, RideTogetherStub.token);
            subscribeCall.enqueue(new BaseCallback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if (!response.isSuccessful()) {
                        Log.d(LOG_TAG, "Get event error : " + response.errorBody());
                        return;
                    }
                    bus.post(new ReceiveRideEvent(response.body()));
                }
            });
        } else {
            Call<Void> unsubscribeCall = eventsService
                    .unsubscribeFromEvent(RideTogetherStub.eventId, RideTogetherStub.token);
            unsubscribeCall.enqueue(new BaseCallback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        Log.d(LOG_TAG, "Get event error : " + response.errorBody());
                        return;
                    }
                    getEvent();
                }
            });
        }
    }

    @Override
    public void getEvents() {
        Call<List<Event>> eventsCall = eventsService.getEvents(null, null, null);
        eventsCall.enqueue(new BaseCallback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                Log.d(LOG_TAG, "Response!");
                if (!response.isSuccessful()) {
                    Log.d(LOG_TAG, "Get event error : " + response.errorBody());
                    return;
                }
                bus.post(new ReceiveRideEvents(response.body()));
            }
        });
    }

    @Override
    public void getEvent() {
        Call<List<Event>> eventsCall = eventsService.getEvents(null, null, null);
        eventsCall.enqueue(new BaseCallback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()) {
                    Log.d(LOG_TAG, "Get event error : " + response.errorBody());
                    return;
                }
                bus.post(new ReceiveRideEvents(response.body()));
            }
        });
    }

    @Override
    public void getComments() {
        Call<List<Comment>> commentsCall = routesService.
                getComments(RideTogetherStub.routeId, null, null, null);
        commentsCall.enqueue(new BaseCallback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    Log.d(LOG_TAG, "Get comment error : " + response.errorBody());
                    return;
                }
                bus.post(new ReceiveCommentsEvent(response.body()));
            }
        });
    }

    @Override
    public void getRoute() {
        Call<Route> routeCall = routesService.getRoute(RideTogetherStub.routeId);
        routeCall.enqueue(new BaseCallback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                if (!response.isSuccessful()) {
                    Log.d(LOG_TAG, "Get route error : " + response.errorBody());
                    return;
                }
                bus.post(new ReceiveRouteEvent(response.body()));
            }
        });
    }
}
