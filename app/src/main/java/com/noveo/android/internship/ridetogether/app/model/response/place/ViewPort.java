package com.noveo.android.internship.ridetogether.app.model.response.place;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class ViewPort {
    @SerializedName("northeast")
    Location northeast;
    @SerializedName("southwest")
    Location southwest;

    ViewPort() {
    }

    public ViewPort(Location northeast, Location southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public Location getNortheast() {
        return northeast;
    }

    public Location getSouthwest() {
        return southwest;
    }
}
