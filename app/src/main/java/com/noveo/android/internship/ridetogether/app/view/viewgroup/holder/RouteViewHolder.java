package com.noveo.android.internship.ridetogether.app.view.viewgroup.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.R;


public class RouteViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.route_desc)
    public TextView description;
    @Bind(R.id.route_distance)
    public TextView distance;
    @Bind(R.id.route_rating)
    public TextView rating;
    @Bind(R.id.route_title)
    public TextView title;

    public RouteViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
