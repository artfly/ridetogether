package com.noveo.android.internship.ridetogether.app.rest.model.route;

import org.parceler.Parcel;

@Parcel
public class Route {
    private String type;
    private Properties properties;
    private LineString geometry;
    public Route(String type, Properties properties, LineString geometry) {
        this.type = type;
        this.properties = properties;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public Properties getProperties() {
        return properties;
    }

    public LineString getGeometry() {
        return geometry;
    }
}
