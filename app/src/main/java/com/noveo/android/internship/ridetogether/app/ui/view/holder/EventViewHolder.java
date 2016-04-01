package com.noveo.android.internship.ridetogether.app.ui.view.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.service.BusProvider;
import com.noveo.android.internship.ridetogether.app.model.event.SubscribeEvent;
import com.noveo.android.internship.ridetogether.app.model.service.ManagerProvider;

public class EventViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.event_desc)
    public TextView eventDescView;
    @Bind(R.id.event_date)
    public TextView eventDateView;
    @Bind(R.id.event_title)
    public TextView eventTitleView;
    @Bind(R.id.button_subscribe)
    public Button subscribeButton;

    private Context context;

    public EventViewHolder(View view, Context context) {
        super(view);
        this.context = context;
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.button_subscribe)
    public void subscribe(Button button) {
        ManagerProvider.getInstance(context).subscribe(button.getText());
        BusProvider.getInstance().post(new SubscribeEvent(button.getText().toString()));
    }
}
