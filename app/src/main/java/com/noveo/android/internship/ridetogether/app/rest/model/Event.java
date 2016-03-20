package com.noveo.android.internship.ridetogether.app.rest.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class Event {
    @SerializedName("route_id")
    private int routeId;
    @SerializedName("creator_id")
    private int creatorId;
    private Date date;
    private String description;
    private List<User> participants;
    private List<User> subscribers;
    private String title;
    private int id;

    public Event(int id, int routeId, Date date, List<User> participants,
                 List<User> subscribers, String description, String title) {
        this.id = id;
        this.routeId = routeId;
        this.date = date;
        this.participants = participants;
        this.subscribers = subscribers;
        this.description = description;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
