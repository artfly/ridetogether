package com.noveo.android.internship.ridetogether.app.model.response.route;

public interface RouteBased {
    RouteViewType getViewType();

    enum RouteViewType {
        ROUTE, COMMENT, ERROR
    }
}
