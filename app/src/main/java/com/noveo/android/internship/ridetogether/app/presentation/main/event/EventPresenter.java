package com.noveo.android.internship.ridetogether.app.presentation.main.event;


import android.content.Context;
import android.net.Credentials;
import android.text.TextUtils;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.RideTogetherStub;
import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.model.rest.service.RoutesService;
import com.noveo.android.internship.ridetogether.app.presentation.common.BasePresenter;
import com.noveo.android.internship.ridetogether.app.presentation.common.SchedulerTransformer;
import com.noveo.android.internship.ridetogether.app.utils.LoginUtil;

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

    public void subscribe(Context context, int eventId, String action) {
        if (eventsService == null) {
            RideTogetherClient client = RideTogetherClient.getInstance();
            eventsService = client.getEventsService();
        }

        String username = LoginUtil.getUsername(context);
        String password = LoginUtil.getPassword(context);
        String auth = LoginUtil.createAuthString(username, password);
        if (TextUtils.equals(action, view.getContext().getString(R.string.subscribe))) {
            eventsService.subscribeToEvent(auth, eventId)
                    .compose(SchedulerTransformer.applySchedulers())
                    .subscribe(event -> view.showEvent(event));
        } else {
            eventsService.unsubscribeFromEvent(auth, eventId)
                    .flatMap(aVoid -> eventsService.getEvent(eventId))
                    .compose(SchedulerTransformer.applySchedulers())
                    .subscribe(event -> view.showEvent(event));
        }
    }

}
