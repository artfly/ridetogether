package com.noveo.android.internship.ridetogether.app;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.gson.Gson;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonLineStringStyle;
import com.noveo.android.internship.ridetogether.app.bus.BusProvider;
import com.noveo.android.internship.ridetogether.app.event.GetRouteEvent;
import com.noveo.android.internship.ridetogether.app.event.ReceiveCommentsEvent;
import com.noveo.android.internship.ridetogether.app.event.ReceiveRouteEvent;
import com.noveo.android.internship.ridetogether.app.rest.model.route.Route;
import com.noveo.android.internship.ridetogether.app.utils.RouteUtil;
import com.noveo.android.internship.ridetogether.app.view.adapter.RouteAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Route route;
    private JSONObject routeObject;
    private boolean isRouteAdded = false;
    private Bus bus = BusProvider.getInstance();
    private List<Object> items = new ArrayList<>();
    private RouteAdapter adapter;
    public static final String ROUTE_ID_TAG = "ROUTE_ID_TAG";

    @Bind(R.id.route_list)
    RecyclerView routeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);
//      routeId = getIntent().getIntExtra(ROUTE_ID_TAG, -1);
        setUpMapIfNeeded();

        routeView.setHasFixedSize(true);
        routeView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RouteAdapter(items, this);
        routeView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        bus.register(this);
        bus.post(new GetRouteEvent());
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    //TODO : is it thread safe?
    @Subscribe
    public void onReceiveRoute(ReceiveRouteEvent event) {
        this.route = event.getRoute();
        try {
            this.routeObject = new JSONObject((new Gson()).toJson(route));
        } catch (JSONException e) {
            Log.e(RouteActivity.class.getSimpleName(), "Error : cannot convert json");
        }
        if (map != null && !isRouteAdded) {
            addRouteToMap();
            isRouteAdded = true;
        }
        RouteUtil.addRouteToItems(items, route);
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onReceiveComments(ReceiveCommentsEvent event) {
        RouteUtil.addCommentsToItems(items, event.getComments());
        if (items.size() > event.getComments().size()) {
            adapter.notifyDataSetChanged();
        }
    }

    private void addRouteToMap() {
        GeoJsonLayer layer = new GeoJsonLayer(map, routeObject);

        GeoJsonLineStringStyle lineStringStyle = new GeoJsonLineStringStyle();
        lineStringStyle.setWidth(4);
        lineStringStyle.setColor(ContextCompat.getColor(this, R.color.light_orange));
        layer.getFeatures().iterator().next().setLineStringStyle(lineStringStyle);

        CameraUpdate update = RouteUtil.getLineStringPosition(layer);
        layer.addLayerToMap();
        if (update != null) {
            map.moveCamera(update);
            CameraPosition position = CameraPosition.builder()
                    .target(map.getCameraPosition().target)
                    .zoom(map.getCameraPosition().zoom - 1)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (!isRouteAdded && route != null) {
            addRouteToMap();
            isRouteAdded = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
