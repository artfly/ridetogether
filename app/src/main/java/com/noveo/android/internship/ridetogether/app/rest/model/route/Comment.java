package com.noveo.android.internship.ridetogether.app.rest.model.route;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Comment {
    @SerializedName("added_at")
    Date date;
    private RequestComment content;
    @SerializedName("creator_id")
    private int creatorId;
    @SerializedName("creator_image_path")
    private String imagePath;
    @SerializedName("creator_name")
    private String creatorName;
    private float rating;

    public Comment(RequestComment content, int creatorId, float rating, Date date) {
        this.content = content;
        this.creatorId = creatorId;
        this.rating = rating;
        this.date = date;
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
