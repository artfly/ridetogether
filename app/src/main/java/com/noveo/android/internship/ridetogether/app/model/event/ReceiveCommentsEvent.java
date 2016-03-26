package com.noveo.android.internship.ridetogether.app.model.event;


import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;

import java.util.List;

public class ReceiveCommentsEvent {
    private List<Comment> comments;

    public ReceiveCommentsEvent(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
