package com.noveo.android.internship.ridetogether.app.model.response;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class User {
    @SerializedName("id")
    long id;
    @SerializedName("username")
    String username;
    @SerializedName("image_path")
    String imagePath;
    @SerializedName("place_id")
    String placeId;
    @SerializedName("route_type")
    String routeType;
    @SerializedName("bike_model")
    String bikeModel;
    @SerializedName("registered_at")
    Date registeredAt;

    public User() {
    }

    public User(long id, String username, String imagePath, String placeId, String routeType, String bikeModel, Date registeredAt) {
        this.id = id;
        this.username = username;
        this.imagePath = imagePath;
        this.placeId = placeId;
        this.routeType = routeType;
        this.bikeModel = bikeModel;
        this.registeredAt = registeredAt;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getRouteType() {
        return routeType;
    }

    public String getBikeModel() {
        return bikeModel;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }
}
