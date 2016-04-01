package com.noveo.android.internship.ridetogether.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.event.*;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.EventBased;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.model.service.Manager;
import com.noveo.android.internship.ridetogether.app.model.service.ManagerProvider;
import com.noveo.android.internship.ridetogether.app.ui.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.ui.utils.IntentUtil;
import com.noveo.android.internship.ridetogether.app.ui.utils.MapUtil;
import com.noveo.android.internship.ridetogether.app.ui.view.adapter.EventAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends BaseActivity {
    @Bind(R.id.event_list)
    RecyclerView eventView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.map_image)
    ImageView mapImage;
    private List<EventBased> items = new ArrayList<>();
    private EventAdapter adapter;
    private Manager manager = ManagerProvider.getInstance(this);
    private Event event;
    private Route route;

    @OnClick(R.id.route_fab)
    public void showRoute() {
        startActivity(IntentUtil.createIntent(this, route));
    }

    @Subscribe
    public void onReceiveRideEvent(ReceiveRideEvent event) {
        EventUtil.updateItems(items, event.getRideEvent(), this);
        adapter.notifyDataSetChanged();
        collapsingToolbar.setTitle(event.getRideEvent().getTitle());
    }

    @Subscribe
    public void onReceiveRouteEvent(ReceiveRouteEvent event) {
        Glide.with(this)
                .load(MapUtil.createMapUrl(event.getRoute()))
                .asBitmap()
                .into(mapImage);
        this.route = event.getRoute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(getString(R.string.app_name));

        event = IntentUtil.getEvent(getIntent());
        if (event != null) {
            EventUtil.updateItems(items, event, this);
            collapsingToolbar.setTitle(event.getTitle());
        }

        eventView.setHasFixedSize(true);
        eventView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(items, this);
        eventView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.getRoute();
    }
}
