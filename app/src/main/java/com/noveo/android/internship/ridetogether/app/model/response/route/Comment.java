package com.noveo.android.internship.ridetogether.app.model.response.route;

import com.google.gson.annotations.SerializedName;
import com.noveo.android.internship.ridetogether.app.model.request.RequestComment;
import com.noveo.android.internship.ridetogether.app.model.request.RequestContent;
import com.noveo.android.internship.ridetogether.app.model.response.event.User;
import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class Comment implements RouteBased {
    @SerializedName("added_at")
    Date addedAt;
    @SerializedName("content")
    RequestContent content;
    @SerializedName("creator")
    User creator;
    @SerializedName("id")
    long id;
    @SerializedName("rating")
    int rating;

    public Comment(Date addedAt, RequestContent content, User creator, long id, int rating, List<String> pics) {
        this.addedAt = addedAt;
        this.content = content;
        this.creator = creator;
        this.id = id;
        this.rating = rating;
    }

    public Comment() {
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public RequestContent getContent() {
        return content;
    }

    public User getCreator() {
        return creator;
    }

    public long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public RouteViewType getViewType() {
        return RouteViewType.COMMENT;
    }
}
