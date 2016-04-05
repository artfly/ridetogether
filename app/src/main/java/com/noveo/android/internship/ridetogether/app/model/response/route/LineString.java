package com.noveo.android.internship.ridetogether.app.model.response.route;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class LineString {
    @SerializedName("type")
    String type;
    @SerializedName("coordinates")
    List<List<Double>> coordinates;

    public LineString() {
    }

    public LineString(String type, List<List<Double>> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }
}
