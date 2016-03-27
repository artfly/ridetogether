package com.noveo.android.internship.ridetogether.app.ui.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.ui.view.DynamicHeightImageView;

public class EventStaggeredViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.event_title_staggered)
    public TextView eventTitleView;
    @Bind(R.id.event_image)
    public DynamicHeightImageView eventImageView;
    @Bind(R.id.event_date_staggered)
    public TextView eventDateView;
    @Bind(R.id.progressBar)
    public ProgressBar progressBar;
    private EventClickListener listener;

    public EventStaggeredViewHolder(View view, EventClickListener listener) {
        super(view);
        this.listener = listener;
        ButterKnife.bind(this, view);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onEventClick((int) getAdapterPosition());
    }

    public interface EventClickListener {
        public void onEventClick(int position);
    }
}
