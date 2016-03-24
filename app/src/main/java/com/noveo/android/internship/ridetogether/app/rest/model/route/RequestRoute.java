package com.noveo.android.internship.ridetogether.app.rest.model.route;

import org.parceler.Parcel;

@Parcel
public class RequestRoute {
    private String type;
    private RequestProperties properties;
    private LineString geometry;

    public RequestRoute(String type, RequestProperties properties, LineString geometry) {

        this.type = type;
        this.properties = properties;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public RequestProperties getProperties() {
        return properties;
    }

    public LineString getGeometry() {
        return geometry;
    }
}
