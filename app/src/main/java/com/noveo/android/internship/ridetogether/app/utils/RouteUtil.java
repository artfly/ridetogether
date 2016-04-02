package com.noveo.android.internship.ridetogether.app.utils;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.model.response.route.RouteBased;

import java.text.DecimalFormat;
import java.util.List;

public final class RouteUtil {

    private RouteUtil() {
    }

    public static String getRouteDistance(List<List<Double>> coordinates) {
        float[] result = new float[1];
        float totalResult = 0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            List<Double> coordinate = coordinates.get(i);
            List<Double> nextCoordinate = coordinates.get(i + 1);
            Location.distanceBetween(coordinate.get(0), coordinate.get(1),
                    nextCoordinate.get(0), nextCoordinate.get(1), result);
            totalResult += result[0];
        }
        return new DecimalFormat("#.#").format(totalResult / 1000) + " km";
    }

    public static void addRouteToItems(List<RouteBased> items, Route route) {
        if (items.size() > 0 && items.get(0).getViewType() == RouteBased.RouteViewType.ROUTE) {
            items.clear();
        }
        items.add(0, route);
    }

    public static void addCommentsToItems(List<RouteBased> items, List<Comment> comments) {
        if (items.size() > 1 ||
                (items.size() == 1 && items.get(0).getViewType() == RouteBased.RouteViewType.COMMENT)) {
            items.clear();
        }
        items.addAll(comments);
    }

    public static LatLng getStartLatLng(Route route) {
        List<Double> start = route.getGeometry().getCoordinates().get(0);
        return new LatLng(start.get(1), start.get(0));
    }

    public static LatLng getEndLatLng(Route route) {
        List<List<Double>> coordinates = route.getGeometry().getCoordinates();
        List<Double> end = coordinates.get(coordinates.size() - 1);
        return new LatLng(end.get(1), end.get(0));
    }
}
