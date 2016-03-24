package com.noveo.android.internship.ridetogether.app.rest.model.route;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class Properties {
    private String name;
    private String description;
    private List<Photo> photos;
    private float rating;
    @SerializedName("added_at")
    private Date date;
    @SerializedName("creator_id")
    private int creatorId;

    public float getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public Properties(String name, String description, List<Photo> photos) {
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
