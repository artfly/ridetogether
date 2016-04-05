package com.noveo.android.internship.ridetogether.app.presentation.main.route;

import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseView;

import java.util.List;

public interface RouteView extends BaseView {
    void showComments(final List<Comment> comments);
}
