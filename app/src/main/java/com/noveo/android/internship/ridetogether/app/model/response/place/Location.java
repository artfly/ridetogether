package com.noveo.android.internship.ridetogether.app.model.response.place;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Location {
    @SerializedName("lat")
    Double latitude;
    @SerializedName("lng")
    Double longitude;

    Location() {
    }

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
