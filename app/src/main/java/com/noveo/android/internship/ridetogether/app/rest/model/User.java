package com.noveo.android.internship.ridetogether.app.rest.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class User {

    private String username;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("image_id")
    private int imageId;

    public User(String username, int userId, int imageId) {
        this.username = username;
        this.userId = userId;
        this.imageId = imageId;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public int getImageId() {
        return imageId;
    }
}
