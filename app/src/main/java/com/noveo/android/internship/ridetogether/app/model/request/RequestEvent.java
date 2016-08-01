package com.noveo.android.internship.ridetogether.app.model.request;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class RequestEvent {
    @SerializedName("route_id")
    long routeId;
    @SerializedName("image_path")
    String imagePath;
    @SerializedName("description")
    String description;
    @SerializedName("title")
    String title;
    @SerializedName("date")
    long date;

    public RequestEvent() {
    }

    public RequestEvent(int routeId, String description, String title, int date, String imagePath) {
        this.routeId = routeId;
        this.description = description;
        this.title = title;
        this.date = date;
        this.imagePath = imagePath;
    }

    public long getRouteId() {
        return routeId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public long getDate() {
        return date;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }
}
