package com.noveo.android.internship.ridetogether.app.ui.utils;

import android.location.Location;
import android.text.format.DateFormat;
import com.google.android.gms.maps.model.LatLng;
import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class RouteUtil {

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

    public static CharSequence dateToString(Date date) {
        return DateFormat.format("dd.MM.yyyy HH:mm", date);
    }

    public static void addRouteToItems(List<Object> items, Route route) {
        if (items.size() > 0 && items.get(0) instanceof Route) {
            items.clear();
        }
        items.add(0, route);
    }

    public static void addCommentsToItems(List<Object> items, List<Comment> comments) {
        if (items.size() > 1 || (items.size() == 1 && items.get(0) instanceof Comment)) {
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
