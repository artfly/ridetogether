package com.noveo.android.internship.ridetogether.app.model.service;

import android.content.Context;
import android.util.Log;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.RideTogetherStub;
import com.noveo.android.internship.ridetogether.app.model.event.*;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.rest.service.RoutesService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;

public class EventManager {
    private Context context;
    private Bus bus;
    private EventsService eventsService;
    private RoutesService routesService;

    public EventManager(Context context, Bus bus) {
        this.context = context;
        this.bus = bus;
        RideTogetherClient client = RideTogetherClient.getInstance();
        this.eventsService = client.getEventsService();
        this.routesService = client.getRoutesService();
    }

    @Subscribe
    public void onGetRideEvent(GetRideEvent event) {
        Call<Event> eventCall = eventsService.getEvent(RideTogetherStub.eventId);
        eventCall.enqueue(new BaseCallback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (!response.isSuccessful()) {
                    Log.d(LOG_TAG, "Get event error : " + response.errorBody());
                    return;
                }
                bus.post(new ReceiveRideEvent(response.body()));
            }
        });
    }

    @Subscribe
    public void onSubscribeEvent(SubscribeEvent event) {
        if (event.getAction().equals(context.getString(R.string.subscribe))) {
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
                    onGetRideEvent(null);
                }
            });
        }
    }

    @Subscribe
    public void onGetRouteEvent(GetRouteEvent event) {
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

    @Subscribe
    public void onGetCommentsEvent(GetCommentsEvent event) {
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
}
