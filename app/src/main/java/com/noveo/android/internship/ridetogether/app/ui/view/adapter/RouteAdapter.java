package com.noveo.android.internship.ridetogether.app.ui.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;
import com.noveo.android.internship.ridetogether.app.model.response.route.Properties;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.ui.utils.RouteUtil;
import com.noveo.android.internship.ridetogether.app.ui.view.holder.CommentViewHolder;
import com.noveo.android.internship.ridetogether.app.ui.view.holder.RouteViewHolder;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ROUTE_TYPE = 0;
    private final int COMMENT_TYPE = 1;
    private List<Object> items;
    private Context context;

    public RouteAdapter(List<Object> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ROUTE_TYPE:
                View routeView = inflater.inflate(R.layout.list_item_route, parent, false);
                viewHolder = new RouteViewHolder(routeView);
                break;
            default:
                View commentView = inflater.inflate(R.layout.list_item_comment, parent, false);
                viewHolder = new CommentViewHolder(commentView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ROUTE_TYPE:
                RouteViewHolder routeViewHolder = (RouteViewHolder) viewHolder;
                configureRouteViewHolder(routeViewHolder, position);
                break;
            default:
                CommentViewHolder commentViewHolder = (CommentViewHolder) viewHolder;
                configureCommentViewHolder(commentViewHolder, position);
        }
    }

    private void configureRouteViewHolder(RouteViewHolder holder, int position) {
        Route route = (Route) items.get(position);
        if (route != null) {
            Properties properties = route.getProperties();
            holder.description.setText(properties.getDescription());
            holder.distance.setText(RouteUtil.getRouteDistance(route.getGeometry().getCoordinates()));
            holder.rating.setText(String.valueOf(properties.getRating()));
            holder.title.setText(properties.getName());
        }
    }

    private void configureCommentViewHolder(final CommentViewHolder holder, int position) {
        Comment comment = (Comment) items.get(position);
        if (comment != null) {
            holder.creator.setText(comment.getCreatorName());
            Glide.with(context)
                    .load(comment.getImagePath())
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(holder.avatar) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circular =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circular.setCircular(true);
                            holder.avatar.setImageDrawable(circular);
                        }
                    });
            holder.date.setText(RouteUtil.dateToString(comment.getDate()));
            holder.text.setText(comment.getContent().getText());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Route) {
            return ROUTE_TYPE;
        } else if (items.get(position) instanceof Comment) {
            return COMMENT_TYPE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
