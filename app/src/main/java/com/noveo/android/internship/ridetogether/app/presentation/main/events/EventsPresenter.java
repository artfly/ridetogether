package com.noveo.android.internship.ridetogether.app.presentation.main.events;

import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.presentation.common.BasePresenter;
import com.noveo.android.internship.ridetogether.app.presentation.common.SchedulerTransformer;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;

public class EventsPresenter extends BasePresenter<EventListView> {
    private EventsService eventsService;

    public void loadEventList() {
        if (eventsService == null) {
            eventsService = RideTogetherClient.getInstance().getEventsService();
        }
        subscription = eventsService.getEvents(null, null, null)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(events -> view.showEvents(events));
    }

    public void loadEventsInRange(EventUtil.Range range) {
        if (eventsService == null) {
            eventsService = RideTogetherClient.getInstance().getEventsService();
        }
        subscription = eventsService.getEvents(null, EventUtil.rangeToSince(range), null)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(events -> view.showEvents(events));
    }
}
