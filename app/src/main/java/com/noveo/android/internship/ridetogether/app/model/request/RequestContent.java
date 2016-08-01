package com.noveo.android.internship.ridetogether.app.model.request;

import com.google.gson.annotations.SerializedName;
import com.noveo.android.internship.ridetogether.app.model.response.route.LineString;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class RequestContent {
    @SerializedName("text")
    String text;
    @SerializedName("geometry")
    LineString geometry;
    @SerializedName("pics")
    List<String> pics;

    public RequestContent() {
    }

    public RequestContent(String text, LineString geometry, List<String> pics) {
        this.text = text;
        this.geometry = geometry;
        this.pics = pics;
    }

    public String getText() {
        return text;
    }

    public LineString getGeometry() {
        return geometry;
    }

    public List<String> getPics() {
        return pics;
    }
}
