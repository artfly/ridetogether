package com.noveo.android.internship.ridetogether.app.manager;

import android.content.Context;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.callback.EventCallback;
import com.noveo.android.internship.ridetogether.app.callback.UnsubscribeCallback;
import com.noveo.android.internship.ridetogether.app.event.GetRideEvent;
import com.noveo.android.internship.ridetogether.app.event.SubscribeEvent;
import com.noveo.android.internship.ridetogether.app.rest.client.EventsClient;
import com.noveo.android.internship.ridetogether.app.rest.model.Event;
import com.noveo.android.internship.ridetogether.app.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.stub.EventStub;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import retrofit2.Call;

/**
 * Created by arty on 20.03.16.
 */
public class EventManager {
    private Context context;
    private Bus bus;
    private EventsClient eventClient;
    private EventsService service;

    public EventManager(Context context, Bus bus) {
        this.context = context;
        this.bus = bus;
        this.eventClient = EventsClient.getInstance();
        this.service = eventClient.getService();
    }

    @Subscribe
    public void onGetRideEvent(GetRideEvent event) {
        EventCallback callback = new EventCallback();
        Call<Event> eventCall = service.getEvent(EventStub.event_id);
        eventCall.enqueue(callback);
    }

    @Subscribe
    public void onSubscribeEvent(SubscribeEvent event) {
        if (event.getAction().equals(context.getString(R.string.subscribe))) {
            //TODO : set button text somewhere
            Call<Event> subscribeCall = service.subscribeToEvent(EventStub.event_id, EventStub.token);
            subscribeCall.enqueue(new EventCallback());
        } else {
            Call<Void> unsubscribeCall = service.unsubscribeFromEvent(EventStub.event_id, EventStub.token);
            unsubscribeCall.enqueue(new UnsubscribeCallback());
        }
    }
}
