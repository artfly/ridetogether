package com.noveo.android.internship.ridetogether.app.presenter;


import android.text.TextUtils;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.RideTogetherStub;
import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.model.rest.service.RoutesService;
import com.noveo.android.internship.ridetogether.app.view.activity.EventMvpView;
import rx.Subscription;

public class EventPresenter implements Presenter<EventMvpView> {
    private EventMvpView eventMvpView;
    private Subscription subscription;
    private RoutesService routesService;
    private EventsService eventsService;

    @Override
    public void attachView(EventMvpView eventMvpView) {
        this.eventMvpView = eventMvpView;
    }

    @Override
    public void detachView() {
        eventMvpView = null;
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public void loadRoute(int routeId) {
        if (routesService == null) {
            RideTogetherClient client = RideTogetherClient.getInstance();
            routesService = client.getRoutesService();
        }
        subscription = routesService.getRoute(routeId)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(route -> eventMvpView.showRoute(route));
    }

    public void subscribe(int eventId, String action) {
        if (eventsService == null) {
            RideTogetherClient client = RideTogetherClient.getInstance();
            eventsService = client.getEventsService();
        }

        if (TextUtils.equals(action, eventMvpView.getContext().getString(R.string.subscribe))) {
            eventsService.subscribeToEvent(eventId, RideTogetherStub.token)
                    .compose(SchedulerTransformer.applySchedulers())
                    .subscribe(event -> eventMvpView.showEvent(event));
        } else {
            eventsService.unsubscribeFromEvent(eventId, RideTogetherStub.token)
                    .compose(SchedulerTransformer.applySchedulers())
                    .flatMap(aVoid -> eventsService.getEvent(eventId))
                    .subscribe(event -> eventMvpView.showEvent(event));
        }
    }

}
