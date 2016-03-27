package com.noveo.android.internship.ridetogether.app.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.event.GetCommentsEvent;
import com.noveo.android.internship.ridetogether.app.model.event.ReceiveCommentsEvent;
import com.noveo.android.internship.ridetogether.app.model.event.ReceiveRouteEvent;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.model.service.BusProvider;
import com.noveo.android.internship.ridetogether.app.ui.utils.MapUtil;
import com.noveo.android.internship.ridetogether.app.ui.utils.RouteUtil;
import com.noveo.android.internship.ridetogether.app.ui.view.adapter.RouteAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String ROUTE_TAG = "ROUTE_TAG";
    @Bind(R.id.route_list)
    RecyclerView routeView;
    @Bind(R.id.route_toolbar)
    Toolbar actionBar;

    private Bus bus = BusProvider.getInstance();

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
        ButterKnife.bind(this);
        //TODO
        setSupportActionBar(actionBar);

        routeView.setHasFixedSize(true);
        routeView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RouteAdapter(items, this);
        routeView.setAdapter(adapter);

        route = Parcels.unwrap(getIntent().getParcelableExtra(ROUTE_TAG));
        if (route != null) {
            setUpMap();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        bus.post(new GetCommentsEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Subscribe
    public void onReceiveComments(ReceiveCommentsEvent event) {
        RouteUtil.addCommentsToItems(items, event.getComments());
        if (items.size() > event.getComments().size()) {
            adapter.notifyDataSetChanged();
        }
    }

    //TODO : is it thread safe?
    @Subscribe
    public void onReceiveRoute(ReceiveRouteEvent event) {
        this.route = event.getRoute();
        setUpMap();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
