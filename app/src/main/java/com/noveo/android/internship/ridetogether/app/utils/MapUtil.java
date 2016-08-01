package com.noveo.android.internship.ridetogether.app.utils;


import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonLineString;
import com.google.maps.android.geojson.GeoJsonLineStringStyle;
import com.noveo.android.internship.ridetogether.app.model.response.route.LineString;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.model.rest.GsonSingleton;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public final class MapUtil {
    private static final String MAP_TAG = "MAP_TAG";
    private static final String MAP_AUTHORITY = "www.maps.google.com";
    // forest green
    private static final String MARKER_COLOR_HEX = "228b22";
    private static final String ROUTE_COLOR = "blue";
    private static final int ROUTE_COLOR_INT = Color.BLUE;
    private static final int ROUTE_WEIGHT = 4;
    private static float MARKER_COLOR_HUE = 80;

    private MapUtil() {
    }

    public static String generateMarkers(List<List<Double>> coordinates) {
        String markers = "color:0x" + MARKER_COLOR_HEX;
        List<Double> startCoordinate = coordinates.get(0);
        List<Double> endCoordinate = coordinates.get(coordinates.size() - 1);
        markers += "|" + startCoordinate.get(1) + "," + startCoordinate.get(0);
        markers += "|" + endCoordinate.get(1) + "," + endCoordinate.get(0);
        return markers;
    }

    public static Uri createMapUrl(Route route) {
        LineString lineString = route.getGeometry();
        List<Double> startCoordinate = lineString.getCoordinates().get(0);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(MAP_AUTHORITY)
                .appendPath("maps")
                .appendPath("api")
                .appendPath("staticmap")
                .appendQueryParameter("center", startCoordinate.get(1) + "," + startCoordinate.get(0))
                .appendQueryParameter("path", generatePath(lineString.getCoordinates()))
                .appendQueryParameter("markers", generateMarkers(lineString.getCoordinates()))
                .appendQueryParameter("zoom", "12")
                .appendQueryParameter("size", "640x320")
                .appendQueryParameter("sensor", "false");
        return builder.build();
    }

    private static String generatePath(List<List<Double>> coordinates) {
        String path = String.format("color:%s|weight:%d", ROUTE_COLOR, ROUTE_WEIGHT);
        for (List<Double> coordinate : coordinates) {
            path += "|" + coordinate.get(1) + "," + coordinate.get(0);
        }
        return path;
    }

    public static Marker addMarker(Marker marker, GoogleMap map, Route route, LatLng position) {
        if (marker != null) {
            marker.remove();
        }
        return map.addMarker(new MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(MARKER_COLOR_HUE)));
    }

    public static Marker addMarker(GoogleMap map, LatLng position) {
        return map.addMarker(new MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(MARKER_COLOR_HUE)));
    }

    public static GeoJsonLayer addRouteToMap(final GoogleMap map, Route route) {
        JSONObject routeObject;
        try {
            routeObject = new JSONObject((GsonSingleton.INSTANCE.getGSON()).toJson(route));
        } catch (JSONException e) {
            Log.e(MAP_TAG, "Error : cannot convert json");
            return null;
        }
        GeoJsonLayer layer = new GeoJsonLayer(map, routeObject);

        GeoJsonLineStringStyle lineStringStyle = new GeoJsonLineStringStyle();
        lineStringStyle.setWidth(ROUTE_WEIGHT);
        lineStringStyle.setColor(ROUTE_COLOR_INT);
        layer.getFeatures().iterator().next().setLineStringStyle(lineStringStyle);

        layer.addLayerToMap();
        return layer;
    }

    public static Polyline addPolyline(final GoogleMap map, LatLng from, LatLng to) {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(ROUTE_COLOR_INT);
        polylineOptions.width(ROUTE_WEIGHT);
        polylineOptions.add(from, to);
        return map.addPolyline(polylineOptions);
    }

    public static void animateCameraToRoute(final GoogleMap map, GeoJsonLayer layer) {
        final CameraUpdate update = getLineStringPosition(layer);
        if (update != null) {
            map.setOnCameraChangeListener(arg0 -> {
                map.moveCamera(update);
                CameraPosition position = CameraPosition.builder()
                        .target(map.getCameraPosition().target)
                        .zoom(map.getCameraPosition().zoom - 1)
                        .build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                map.setOnCameraChangeListener(null);
            });
        }
    }

    public static void animateCameraToPosition(final GoogleMap map, LatLng location) {
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(location)
                .zoom(12)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

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
        return (GeoJsonLineString) feature.getGeometry();
    }
}
