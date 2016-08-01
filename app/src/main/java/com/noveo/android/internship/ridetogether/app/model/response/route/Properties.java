package com.noveo.android.internship.ridetogether.app.model.response.route;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class Properties {
    @SerializedName("id")
    long id;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
//    @SerializedName("photos")
//    List<Photo> photos;
    @SerializedName("rating")
    float rating;
    @SerializedName("added_at")
    Date date;
    @SerializedName("creator_id")
    long creatorId;
    @SerializedName("route_type")
    String routeType;
    @SerializedName("place_id")
    String placeId;

    public Properties() {
    }

    public Properties(long id, String title, String description, float rating, Date date, long creatorId, String routeType, String placeId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.date = date;
        this.creatorId = creatorId;
        this.routeType = routeType;
        this.placeId = placeId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public Date getDate() {
        return date;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public String getRouteType() {
        return routeType;
    }

    public String getPlaceId() {
        return placeId;
    }
}
