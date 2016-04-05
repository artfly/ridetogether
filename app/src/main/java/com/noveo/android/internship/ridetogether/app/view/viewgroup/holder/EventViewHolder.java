package com.noveo.android.internship.ridetogether.app.view.viewgroup.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.view.bus.BusProvider;
import com.noveo.android.internship.ridetogether.app.view.bus.event.SubscribeEvent;

public class EventViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.event_desc)
    public TextView eventDescView;
    @Bind(R.id.event_date)
    public TextView eventDateView;
    @Bind(R.id.event_title)
    public TextView eventTitleView;
    @Bind(R.id.button_subscribe)
    public Button subscribeButton;

    public EventViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.button_subscribe)
    public void subscribe(Button button) {
        BusProvider.getInstance().post(new SubscribeEvent(button.getText().toString()));
    }
}
