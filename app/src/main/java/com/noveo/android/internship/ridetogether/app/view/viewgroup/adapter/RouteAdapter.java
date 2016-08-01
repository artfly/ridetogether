package com.noveo.android.internship.ridetogether.app.view.viewgroup.adapter;

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
import com.noveo.android.internship.ridetogether.app.model.response.route.RouteBased;
import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.holder.CommentViewHolder;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.holder.ErrorViewHolder;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.holder.RouteViewHolder;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.utils.RouteUtil;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RouteBased> items;
    private Context context;

    public RouteAdapter(List<RouteBased> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (RouteBased.RouteViewType.values()[viewType]) {
            case ROUTE:
                View routeView = inflater.inflate(R.layout.list_item_route, parent, false);
                viewHolder = new RouteViewHolder(routeView);
                break;
            case COMMENT:
                View commentView = inflater.inflate(R.layout.list_item_comment, parent, false);
                viewHolder = new CommentViewHolder(commentView);
                break;
            default:
                View errorView = inflater.inflate(R.layout.list_item_error, parent, false);
                viewHolder = new ErrorViewHolder(errorView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (RouteBased.RouteViewType.values()[viewHolder.getItemViewType()]) {
            case ROUTE:
                RouteViewHolder routeViewHolder = (RouteViewHolder) viewHolder;
                configureRouteViewHolder(routeViewHolder, position);
                break;
            case COMMENT:
                CommentViewHolder commentViewHolder = (CommentViewHolder) viewHolder;
                configureCommentViewHolder(commentViewHolder, position);
                break;
        }
    }

    private void configureRouteViewHolder(RouteViewHolder holder, int position) {
        Route route = (Route) items.get(position);
        if (route != null) {
            Properties properties = route.getProperties();
            holder.description.setText(properties.getDescription());
            holder.distance.setText(RouteUtil.getRouteDistance(route.getGeometry().getCoordinates()));
            holder.rating.setText(String.valueOf(properties.getRating()));
            holder.title.setText(properties.getTitle());
        }
    }

    private void configureCommentViewHolder(final CommentViewHolder holder, int position) {
        Comment comment = (Comment) items.get(position);
        if (comment != null) {
            holder.creator.setText(comment.getCreator().getUsername());
            Glide.with(context)
                    .load(RideTogetherClient.getImageUrl(comment.getCreator().getImagePath()))
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
            holder.date.setText(EventUtil.dateToString(comment.getAddedAt()));
            holder.text.setText(comment.getContent().getText());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
