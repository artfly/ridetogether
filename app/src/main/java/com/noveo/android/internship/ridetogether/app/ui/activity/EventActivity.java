package com.noveo.android.internship.ridetogether.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.event.*;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.model.service.BusProvider;
import com.noveo.android.internship.ridetogether.app.ui.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.ui.utils.MapUtil;
import com.noveo.android.internship.ridetogether.app.ui.view.adapter.EventAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private final static String EVENT_TAG = "EVENT_TAG";
    @Bind(R.id.event_list)
    RecyclerView eventView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.map_image)
    ImageView mapImage;
    private List<Object> items = new ArrayList<>();
    private EventAdapter adapter;
    private Bus bus = BusProvider.getInstance();
    private Event event;
    private Route route;

    public static Intent createIntent(final Context context, final Event event) {
        final Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(EVENT_TAG, Parcels.wrap(event));
        return intent;
    }

    @OnClick(R.id.route_fab)
    public void showRoute() {
        Intent showMapIntent = new Intent(this, RouteActivity.class);
        showMapIntent.putExtra(RouteActivity.ROUTE_TAG, Parcels.wrap(route));
        startActivity(showMapIntent);
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
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(getString(R.string.app_name));

        event = Parcels.unwrap(getIntent().getParcelableExtra(EVENT_TAG));
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
        bus.register(this);
        bus.post(new GetRouteEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
