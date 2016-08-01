package com.noveo.android.internship.ridetogether.app.model.response.place;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Geometry {
    @SerializedName("location")
    Location location;
    @SerializedName("viewport")
    ViewPort viewPort;

    Geometry() {
    }

    public Geometry(Location location, ViewPort viewPort) {
        this.location = location;
        this.viewPort = viewPort;
    }

    public Location getLocation() {
        return location;
    }

    public ViewPort getViewPort() {
        return viewPort;
    }
}
