package com.noveo.android.internship.ridetogether.app.rest.model.route;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class LineString {
    String type;
    List<List<Double>> coordinates;

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
