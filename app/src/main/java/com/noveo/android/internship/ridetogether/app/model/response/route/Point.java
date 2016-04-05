package com.noveo.android.internship.ridetogether.app.model.response.route;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Point {
    @SerializedName("type")
    String type;
    @SerializedName("coordinates")
    List<Float> coordinates;

    public Point() {
    }

    public Point(String type, List<Float> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public List<Float> getCoordinates() {
        return coordinates;
    }

    public String getType() {
        return type;
    }
}
