package com.noveo.android.internship.ridetogether.app.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.response.event.EventBased;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.presenter.EventPresenter;
import com.noveo.android.internship.ridetogether.app.presenter.Presenter;
import com.noveo.android.internship.ridetogether.app.view.bus.event.SubscribeEvent;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.adapter.EventAdapter;
import com.noveo.android.internship.ridetogether.app.utils.EventUtil;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;
import com.noveo.android.internship.ridetogether.app.utils.MapUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends BaseMvpActivity implements EventMvpView {
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
    private EventPresenter presenter = new EventPresenter();

    private Event event;
    private Route route;

    @OnClick(R.id.route_fab)
    public void showRouteOnMap() {
        startActivity(IntentUtil.createIntent(this, route, event.getRouteId()));
    }

    @Subscribe
    public void onSubscribeEvent(SubscribeEvent subscribeEvent) {
        presenter.subscribe(event.getId(), subscribeEvent.getAction());
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
            presenter.loadRoute(event.getRouteId());
        }

        eventView.setHasFixedSize(true);
        eventView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(items, this);
        eventView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showEvent(Event event) {
        EventUtil.updateItems(items, event, this);
        adapter.notifyDataSetChanged();
        collapsingToolbar.setTitle(event.getTitle());
    }

    @Override
    public void showRoute(Route route) {
        this.route = route;
        Glide.with(this)
                .load(MapUtil.createMapUrl(route))
                .asBitmap()
                .into(mapImage);
    }

    @Override
    public void attachPresenter() {
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        presenter.detachView();
    }
}
