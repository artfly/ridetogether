package com.noveo.android.internship.ridetogether.app.rest.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class RequestEvent {
    @SerializedName("route_id")
    private int routeId;
    private String description;
    private String title;
    private int date;

    public int getRouteId() {
        return routeId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getDate() {
        return date;
    }
}
