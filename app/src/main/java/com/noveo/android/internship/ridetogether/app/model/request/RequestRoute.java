package com.noveo.android.internship.ridetogether.app.model.request;

import com.noveo.android.internship.ridetogether.app.model.response.route.LineString;
import org.parceler.Parcel;

@Parcel
public class RequestRoute {
    String type;
    RequestProperties properties;
    LineString geometry;

    public RequestRoute() {
    }

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
