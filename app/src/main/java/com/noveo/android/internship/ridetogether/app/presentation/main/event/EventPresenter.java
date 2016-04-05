package com.noveo.android.internship.ridetogether.app.presentation.main.event;


import android.text.TextUtils;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.RideTogetherStub;
import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.model.rest.service.RoutesService;
import com.noveo.android.internship.ridetogether.app.presentation.common.BasePresenter;
import com.noveo.android.internship.ridetogether.app.presentation.common.SchedulerTransformer;

public class EventPresenter extends BasePresenter<EventView> {
    private RoutesService routesService;
    private EventsService eventsService;

    public void loadRoute(int routeId) {
        if (routesService == null) {
            RideTogetherClient client = RideTogetherClient.getInstance();
            routesService = client.getRoutesService();
        }
        subscription = routesService.getRoute(routeId)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(route -> view.showRoute(route));
    }

    public void subscribe(int eventId, String action) {
        if (eventsService == null) {
            RideTogetherClient client = RideTogetherClient.getInstance();
            eventsService = client.getEventsService();
        }

        if (TextUtils.equals(action, view.getContext().getString(R.string.subscribe))) {
            eventsService.subscribeToEvent(eventId, RideTogetherStub.token)
                    .compose(SchedulerTransformer.applySchedulers())
                    .subscribe(event -> view.showEvent(event));
        } else {
            eventsService.unsubscribeFromEvent(eventId, RideTogetherStub.token)
                    .compose(SchedulerTransformer.applySchedulers())
                    .flatMap(aVoid -> eventsService.getEvent(eventId))
                    .subscribe(event -> view.showEvent(event));
        }
    }

}
