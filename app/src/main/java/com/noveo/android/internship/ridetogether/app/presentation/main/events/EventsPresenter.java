package com.noveo.android.internship.ridetogether.app.presentation.main.events;

import android.text.TextUtils;
import android.util.Log;
import com.noveo.android.internship.ridetogether.app.model.response.place.Place;
import com.noveo.android.internship.ridetogether.app.model.response.place.Places;
import com.noveo.android.internship.ridetogether.app.model.rest.GooglePlacesClient;
import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.model.rest.service.PlacesService;
import com.noveo.android.internship.ridetogether.app.presentation.common.BasePresenter;
import com.noveo.android.internship.ridetogether.app.presentation.common.SchedulerTransformer;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;

import java.io.IOException;

public class EventsPresenter extends BasePresenter<EventListView> {
    private EventsService eventsService;
    private PlacesService placesService;

    public void loadEventList(String placeId) {
        if (eventsService == null) {
            eventsService = RideTogetherClient.getInstance().getEventsService();
        }
        subscription = eventsService.getEvents(placeId, null, null, null)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(events -> view.showEvents(events));
    }

    public void loadEventsInRange(EventUtil.Range range) {
        if (eventsService == null) {
            eventsService = RideTogetherClient.getInstance().getEventsService();
        }
        subscription = eventsService.getEvents("omsk", null, EventUtil.rangeToSince(range), null)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(events -> view.showEvents(events));
    }

    public void getPlaceId(double latitude, double longitude, String key) {
        if (placesService == null) {
            placesService = GooglePlacesClient.getInstance().getPlacesService();
        }
        subscription = placesService.getPlaceId(String.format("%s,%s", latitude, longitude), key, 1)
                .compose(SchedulerTransformer.applySchedulers())
                .flatMapIterable(Places::getResults)
                .filter(place -> TextUtils.join(" ", place.getTypes()).contains("locality"))
                .last()
                .subscribe(place -> loadEventList(place.getPlaceId()));
    }
}
