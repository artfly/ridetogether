package com.noveo.android.internship.ridetogether.app.rest.model.route;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class RequestProperties {
    private String name;
    private String description;
    private List<Photo> photos;

    public RequestProperties(String name, String description, List<Photo> photos) {
        this.name = name;
        this.description = description;
        this.photos = photos;
    }

    public String getName() {

        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
