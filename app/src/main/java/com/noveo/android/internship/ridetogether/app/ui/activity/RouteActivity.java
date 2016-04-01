package com.noveo.android.internship.ridetogether.app.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.event.ReceiveCommentsEvent;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.model.service.Manager;
import com.noveo.android.internship.ridetogether.app.model.service.ManagerProvider;
import com.noveo.android.internship.ridetogether.app.ui.utils.IntentUtil;
import com.noveo.android.internship.ridetogether.app.ui.utils.MapUtil;
import com.noveo.android.internship.ridetogether.app.ui.utils.RouteUtil;
import com.noveo.android.internship.ridetogether.app.ui.view.adapter.RouteAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends BaseActivity implements OnMapReadyCallback {
    @Bind(R.id.route_list)
    RecyclerView routeView;
    @Bind(R.id.route_toolbar)
    Toolbar toolbar;

    private Manager manager = ManagerProvider.getInstance(this);

    private GoogleMap map;
    private Route route;
    private Marker startMarker;
    private Marker endMarker;

    private List<Object> items = new ArrayList<>();
    private RouteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        routeView.setHasFixedSize(true);
        routeView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RouteAdapter(items, this);
        routeView.setAdapter(adapter);

        route = IntentUtil.getRoute(getIntent());
        if (route != null) {
            setUpMap();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.getComments();
    }

    @Subscribe
    public void onReceiveComments(ReceiveCommentsEvent event) {
        RouteUtil.addCommentsToItems(items, event.getComments());
        if (items.size() > event.getComments().size()) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (route != null) {
            startMarker = MapUtil.addMarker(startMarker, map, route, RouteUtil.getStartLatLng(route));
            endMarker = MapUtil.addMarker(endMarker, map, route, RouteUtil.getEndLatLng(route));
            MapUtil.addRouteToMap(map, route);
        }
    }

    private void setUpMap() {
        if (map == null) {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        }

        RouteUtil.addRouteToItems(items, route);
        adapter.notifyDataSetChanged();
    }
}
