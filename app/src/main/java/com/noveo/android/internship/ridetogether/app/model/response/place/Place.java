package com.noveo.android.internship.ridetogether.app.model.response.place;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Place {
    @SerializedName("geometry")
    Geometry geometry;
    @SerializedName("icon")
    String icon;
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("place_id")
    String placeId;
    @SerializedName("reference")
    String reference;
    @SerializedName("scope")
    String scope;
    @SerializedName("types")
    List<String> types;
    @SerializedName("vicinity")
    String vicinity;

    Place() {
    }

    public Place(Geometry geometry, String icon, String id, String name,
                 String placeId, String reference, String scope, List<String> types, String vicinity) {
        this.geometry = geometry;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.placeId = placeId;
        this.reference = reference;
        this.scope = scope;
        this.types = types;
        this.vicinity = vicinity;
    }

    public String getPlaceId() {
        return placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getReference() {
        return reference;
    }

    public String getScope() {
        return scope;
    }

    public String getVicinity() {
        return vicinity;
    }
}
