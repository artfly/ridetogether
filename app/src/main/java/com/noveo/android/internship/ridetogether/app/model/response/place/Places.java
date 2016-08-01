package com.noveo.android.internship.ridetogether.app.model.response.place;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Places {
    @SerializedName("html_attributions")
    List<String> htmlAttributions;
    @SerializedName("results")
    List<Place> results;
    @SerializedName("status")
    String status;

    Places() {
    }

    public Places(List<String> htmlAttributions, List<Place> results, String status) {
        this.htmlAttributions = htmlAttributions;
        this.results = results;
        this.status = status;
    }

    public List<Place> getResults() {
        return results;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public String getStatus() {
        return status;
    }
}
