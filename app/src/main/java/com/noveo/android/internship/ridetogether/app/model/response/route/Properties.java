package com.noveo.android.internship.ridetogether.app.model.response.route;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class Properties {
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("photos")
    List<Photo> photos;
    @SerializedName("rating")
    float rating;
    @SerializedName("added_at")
    Date date;
    @SerializedName("creator_id")
    int creatorId;

    public Properties() {
    }

    public Properties(String name, String description, List<Photo> photos) {
        this.name = name;
        this.description = description;
        this.photos = photos;
    }

    public float getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }

    public int getCreatorId() {
        return creatorId;
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
