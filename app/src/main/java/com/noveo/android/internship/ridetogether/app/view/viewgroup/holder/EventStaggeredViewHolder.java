package com.noveo.android.internship.ridetogether.app.view.viewgroup.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.DynamicHeightImageView;

public class EventStaggeredViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.event_title_staggered)
    public TextView eventTitleView;
    @Bind(R.id.event_image)
    public DynamicHeightImageView eventImageView;
    @Bind(R.id.event_date_staggered)
    public TextView eventDateView;
    @Bind(R.id.progressBar)
    public ProgressBar progressBar;

    public EventStaggeredViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
