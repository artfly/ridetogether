package com.noveo.android.internship.ridetogether.app.presenter;

import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.RoutesService;
import com.noveo.android.internship.ridetogether.app.view.activity.RouteMvpView;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class RoutePresenter implements Presenter<RouteMvpView> {
    private RouteMvpView routeMvpView;
    private Subscription subscription;
    private RoutesService routesService;

    @Override
    public void attachView(RouteMvpView routeMvpView) {
        this.routeMvpView = routeMvpView;
    }

    @Override
    public void detachView() {
        this.routeMvpView = null;
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public void loadComments(int routeId) {
        if (routesService == null) {
            RideTogetherClient client = RideTogetherClient.getInstance();
            routesService = client.getRoutesService();
        }
        subscription = routesService.getComments(routeId, null, null, null)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(comments -> routeMvpView.showComments(comments));
    }
}
