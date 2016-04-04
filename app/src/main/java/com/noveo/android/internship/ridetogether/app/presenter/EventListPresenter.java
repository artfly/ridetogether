package com.noveo.android.internship.ridetogether.app.presenter;

import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.view.activity.EventListMvpView;
import rx.Subscription;

public class EventListPresenter implements Presenter<EventListMvpView> {
    private EventListMvpView listMvpView;
    private EventsService eventsService;
    private Subscription subscription;

    @Override
    public void attachView(EventListMvpView listMvpView) {
        this.listMvpView = listMvpView;
    }

    @Override
    public void detachView() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        this.listMvpView = null;
    }

    public void loadEventList() {
        if (eventsService == null) {
            eventsService = RideTogetherClient.getInstance().getEventsService();
        }
        subscription = eventsService.getEvents(null, null, null)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(events -> listMvpView.showEvents(events));
    }
}
