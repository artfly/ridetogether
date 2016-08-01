package com.noveo.android.internship.ridetogether.app.model.request;

import com.google.gson.annotations.SerializedName;
import com.noveo.android.internship.ridetogether.app.model.response.route.LineString;
import com.noveo.android.internship.ridetogether.app.model.response.route.Photo;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class RequestComment {
    @SerializedName("content")
    RequestContent content;
    public RequestComment() {
    }

    public RequestComment(RequestContent content) {
        this.content = content;
    }

    public RequestContent getContent() {
        return content;
    }
}
