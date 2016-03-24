package com.noveo.android.internship.ridetogether.app.manager;

import android.content.Context;
import android.util.Log;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.bus.BusProvider;
import com.noveo.android.internship.ridetogether.app.callback.BaseCallback;
import com.noveo.android.internship.ridetogether.app.callback.EventCallback;
import com.noveo.android.internship.ridetogether.app.callback.UnsubscribeCallback;
import com.noveo.android.internship.ridetogether.app.event.*;
import com.noveo.android.internship.ridetogether.app.rest.client.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.rest.model.Event;
import com.noveo.android.internship.ridetogether.app.rest.model.route.Comment;
import com.noveo.android.internship.ridetogether.app.rest.model.route.Route;
import com.noveo.android.internship.ridetogether.app.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.rest.service.RoutesService;
import com.noveo.android.internship.ridetogether.app.stub.RideTogetherStub;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class EventManager {
    private Context context;
    private Bus bus;
    private RideTogetherClient client;
    private EventsService eventsService;
    private RoutesService routesService;

    public EventManager(Context context, Bus bus) {
        this.context = context;
        this.bus = bus;
        this.client = RideTogetherClient.getInstance();
        this.eventsService = client.getEventsService();
        this.routesService = client.getRoutesService();
    }

    @Subscribe
    public void onGetRideEvent(GetRideEvent event) {
        EventCallback callback = new EventCallback();
        Call<Event> eventCall = eventsService.getEvent(RideTogetherStub.eventId);
        eventCall.enqueue(callback);
    }

    @Subscribe
    public void onSubscribeEvent(SubscribeEvent event) {
        if (event.getAction().equals(context.getString(R.string.subscribe))) {
            Call<Event> subscribeCall = eventsService.subscribeToEvent(RideTogetherStub.eventId, RideTogetherStub.token);
            subscribeCall.enqueue(new EventCallback());
        } else {
            Call<Void> unsubscribeCall = eventsService.unsubscribeFromEvent(RideTogetherStub.eventId, RideTogetherStub.token);
            unsubscribeCall.enqueue(new UnsubscribeCallback());
        }
    }

    @Subscribe
    public void onGetRouteEvent(GetRouteEvent event) {
        onGetCommentsEvent(null);
        Call<Route> routeCall = routesService.getRoute(RideTogetherStub.routeId);
        routeCall.enqueue(new BaseCallback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                if (!response.isSuccessful()) {
                    Log.d(LOG_TAG, "Get route error : " + response.errorBody());
                    return;
                }
                BusProvider.getInstance().post(new ReceiveRouteEvent(response.body()));
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
                BusProvider.getInstance().post(new ReceiveCommentsEvent(response.body()));
            }
        });
    }
}
