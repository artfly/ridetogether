package com.noveo.android.internship.ridetogether.app.model.response;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Image {
    @SerializedName("image_path")
    String imagePath;
    @SerializedName("longitude")
    double longitude;
    @SerializedName("latitude")
    double latitude;
    @SerializedName("creator")
    long creatorId;

    public Image() {
    }

    public Image(String imagePath, double longitude, double latitude, long creatorId) {
        this.imagePath = imagePath;
        this.longitude = longitude;
        this.latitude = latitude;
        this.creatorId = creatorId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getCreatorId() {
        return creatorId;
    }
}
