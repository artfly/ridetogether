package com.noveo.android.internship.ridetogether.app.utils;

import android.location.Location;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonLineString;
import com.noveo.android.internship.ridetogether.app.rest.model.route.Comment;
import com.noveo.android.internship.ridetogether.app.rest.model.route.LineString;
import com.noveo.android.internship.ridetogether.app.rest.model.route.Route;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RouteUtil {

    public static CameraUpdate getLineStringPosition(GeoJsonLayer layer) {
        GeoJsonLineString lineString = getLineString(layer);
        if (lineString == null) {
            return null;
        }
        lineString.getCoordinates();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng coordinate : lineString.getCoordinates()) {
            builder.include(coordinate);
        }
        LatLngBounds bounds = builder.build();
        return CameraUpdateFactory.newLatLngBounds(bounds, 10);
    }

    private static GeoJsonLineString getLineString(GeoJsonLayer layer) {
        GeoJsonFeature feature = layer.getFeatures().iterator().next();
        //TODO : more checking
        if (feature == null) {
            return null;
        }
        return (GeoJsonLineString) feature.getGeometry();
    }

    public static String getRouteDistance(LineString lineString) {
        List<List<Double>> coordinates = lineString.getCoordinates();
        float[] result = new float[1];
        float totalResult = 0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            List<Double> coordinate = coordinates.get(i);
            List<Double> nextCoordinate = coordinates.get(i + 1);
            Location.distanceBetween(coordinate.get(0), coordinate.get(1),
                    nextCoordinate.get(0), nextCoordinate.get(1), result);
            totalResult += result[0];
        }
        return new DecimalFormat("#.#").format(totalResult / 1000);
    }

    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static void addRouteToItems(List<Object> items, Route route) {
        if (items.size() > 0 && items.get(0) instanceof Route) {
            items.clear();
        }
        items.add(0, route);
    }

    public static void addCommentsToItems(List<Object> items, List<Comment> comments) {
        if (items.size() > 1) {
            items.clear();
        }
        items.addAll(comments);
    }
}
