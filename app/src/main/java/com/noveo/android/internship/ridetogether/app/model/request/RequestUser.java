package com.noveo.android.internship.ridetogether.app.model.request;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class RequestUser {
    @SerializedName("image_path")
    String imagePath;
    @SerializedName("place_id")
    String placeId;
    @SerializedName("route_type")
    String routeType;
    @SerializedName("bike_model")
    String bikeModel;
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;

    public RequestUser() {
    }

    public RequestUser(String imagePath, String placeId, String routeType, String bikeModel, String username, String password) {
        this.imagePath = imagePath;
        this.placeId = placeId;
        this.routeType = routeType;
        this.bikeModel = bikeModel;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
