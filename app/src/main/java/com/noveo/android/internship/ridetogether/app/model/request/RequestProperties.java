package com.noveo.android.internship.ridetogether.app.model.request;

import com.google.gson.annotations.SerializedName;
import com.noveo.android.internship.ridetogether.app.model.response.route.Photo;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class RequestProperties {
    @SerializedName("title")
    String title;
    @SerializedName("place_id")
    String placeId;
    @SerializedName("description")
    String description;
    @SerializedName("route_type")
    String routeType;
    //TODO
//    @SerializedName("photos")
//    List<Photo> photos;

    public RequestProperties() {
    }

    public RequestProperties(String title, String placeId, String description, String routeType) {
        this.title = title;
        this.placeId = placeId;
        this.description = description;
        this.routeType = routeType;
    }

    public String getTitle() {
        return title;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getDescription() {
        return description;
    }

    public String getRouteType() {
        return routeType;
    }
}
