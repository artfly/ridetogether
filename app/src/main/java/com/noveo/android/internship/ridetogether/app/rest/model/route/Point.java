package com.noveo.android.internship.ridetogether.app.rest.model.route;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Point {
    private String type;
    private List<Float> coordinates;

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
