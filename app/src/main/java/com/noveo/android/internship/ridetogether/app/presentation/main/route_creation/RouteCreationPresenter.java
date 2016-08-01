package com.noveo.android.internship.ridetogether.app.presentation.main.route_creation;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.noveo.android.internship.ridetogether.app.model.request.RequestEvent;
import com.noveo.android.internship.ridetogether.app.model.request.RequestProperties;
import com.noveo.android.internship.ridetogether.app.model.request.RequestRoute;
import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.model.rest.service.RoutesService;
import com.noveo.android.internship.ridetogether.app.presentation.common.BasePresenter;
import com.noveo.android.internship.ridetogether.app.presentation.common.SchedulerTransformer;
import com.noveo.android.internship.ridetogether.app.utils.LoginUtil;
import com.noveo.android.internship.ridetogether.app.utils.RouteUtil;

import java.util.List;


public class RouteCreationPresenter extends BasePresenter<RouteCreationView> {
    private RoutesService routeService;
    private EventsService eventService;

    public void addRoute(Context context, RequestProperties properties, List<LatLng> markers) {
        if (routeService == null) {
            routeService = RideTogetherClient.getInstance().getRoutesService();
        }
        RequestRoute requestRoute = RouteUtil.createRoute(properties, markers);
        String username = LoginUtil.getUsername(context);
        String password = LoginUtil.getPassword(context);
        subscription = routeService.postRoute(LoginUtil.createAuthString(username, password), requestRoute)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(route -> view.addEvent(route.getProperties().getId()));
    }

    public void postEvent(Context context, RequestEvent requestEvent) {
        if (eventService == null) {
            eventService = RideTogetherClient.getInstance().getEventsService();
        }
        String username = LoginUtil.getUsername(context);
        String password = LoginUtil.getPassword(context);
        subscription = eventService.postEvent(LoginUtil.createAuthString(username, password), requestEvent)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(event -> {
                    Log.e("LOG_TAG", view.toString());
                    view.onCreation();
                });
    }
}
