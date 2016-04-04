package com.noveo.android.internship.ridetogether.app.view.activity;

import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;

import java.util.List;

public interface RouteMvpView extends MvpView {
    void showComments(final List<Comment> comments);
}
