package com.noveo.android.internship.ridetogether.app.model.request;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class RequestEvent {
    @SerializedName("route_id")
    int routeId;
    @SerializedName("image_path")
    String imagePath;
    @SerializedName("description")
    String description;
    @SerializedName("title")
    String title;
    @SerializedName("date")
    int date;

    public RequestEvent() {
    }

    public RequestEvent(int routeId, String description, String title, int date, String imagePath) {
        this.routeId = routeId;
        this.description = description;
        this.title = title;
        this.date = date;
        this.imagePath = imagePath;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getDate() {
        return date;
    }

    public String getImagePath() {
        return imagePath;
    }
}
