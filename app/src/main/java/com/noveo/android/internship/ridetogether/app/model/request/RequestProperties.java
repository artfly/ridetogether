package com.noveo.android.internship.ridetogether.app.model.request;

import com.noveo.android.internship.ridetogether.app.model.response.route.Photo;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class RequestProperties {
    String name;
    String description;
    List<Photo> photos;

    public RequestProperties() {
    }

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