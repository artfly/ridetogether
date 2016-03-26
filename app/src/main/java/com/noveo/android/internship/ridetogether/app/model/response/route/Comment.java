package com.noveo.android.internship.ridetogether.app.model.response.route;

import com.google.gson.annotations.SerializedName;
import com.noveo.android.internship.ridetogether.app.model.request.RequestComment;
import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Comment {
    @SerializedName("added_at")
    Date date;
    RequestComment content;
    @SerializedName("creator_id")
    int creatorId;
    @SerializedName("creator_image_path")
    String imagePath;
    @SerializedName("creator_name")
    String creatorName;
    float rating;

    public Comment(RequestComment content, int creatorId, float rating, Date date) {
        this.content = content;
        this.creatorId = creatorId;
        this.rating = rating;
        this.date = date;
    }

    public Comment() {
    }

    public Date getDate() {
        return date;
    }

    public float getRating() {
        return rating;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public RequestComment getContent() {
        return content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCreatorName() {
        return creatorName;
    }
}
