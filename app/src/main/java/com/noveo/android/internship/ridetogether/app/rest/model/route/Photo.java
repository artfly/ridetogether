package com.noveo.android.internship.ridetogether.app.rest.model.route;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Photo {
    @SerializedName("image_path")
    private String path;
    private Point geometry;

    public Photo(String path, Point geometry) {
        this.path = path;
        this.geometry = geometry;
    }

    public String getPath() {
        return path;
    }

    public Point getGeometry() {
        return geometry;
    }
}
