package com.noveo.android.internship.ridetogether.app.presentation.main.route;

import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.RoutesService;
import com.noveo.android.internship.ridetogether.app.presentation.common.BasePresenter;
import com.noveo.android.internship.ridetogether.app.presentation.common.SchedulerTransformer;

public class RoutePresenter extends BasePresenter<RouteView> {
    private RoutesService routesService;

    @Override
    public void attachView(RouteView routeMvpView) {
        this.view = routeMvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
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
                .subscribe(comments -> view.showComments(comments));
    }
}
