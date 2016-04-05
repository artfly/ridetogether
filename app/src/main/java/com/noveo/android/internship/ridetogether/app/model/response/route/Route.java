package com.noveo.android.internship.ridetogether.app.model.response.route;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Route implements RouteBased {
    @SerializedName("type")
    String type;
    @SerializedName("properties")
    Properties properties;
    @SerializedName("geometry")
    LineString geometry;

    public Route() {
    }

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

    @Override
    public RouteViewType getViewType() {
        return RouteViewType.ROUTE;
    }
}
