package com.noveo.android.internship.ridetogether.app.rest.model.route;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class RequestComment {
    String text;
    LineString geometry;
    List<Photo> photos;

    public RequestComment(String text, LineString geometry, List<Photo> photos) {
        this.text = text;
        this.geometry = geometry;
        this.photos = photos;
    }

    public String getText() {
        return text;
    }

    public LineString getGeometry() {
        return geometry;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
