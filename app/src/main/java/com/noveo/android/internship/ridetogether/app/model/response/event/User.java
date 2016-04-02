package com.noveo.android.internship.ridetogether.app.model.response.event;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class User implements EventBased {
    String username;
    @SerializedName("user_id")
    int userId;
    @SerializedName("image_path")
    String imagePath;

    public User() {
    }

    public User(String username, int userId, String imagePath) {
        this.username = username;
        this.userId = userId;
        this.imagePath = imagePath;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public EventViewType getViewType() {
        return EventViewType.USER;
    }
}
