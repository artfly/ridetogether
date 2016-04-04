package com.noveo.android.internship.ridetogether.app.view.activity;

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
import com.google.maps.android.geojson.GeoJsonLayer;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.RideTogetherStub;
import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.model.response.route.RouteBased;
import com.noveo.android.internship.ridetogether.app.presenter.RoutePresenter;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;
import com.noveo.android.internship.ridetogether.app.utils.MapUtil;
import com.noveo.android.internship.ridetogether.app.utils.RouteUtil;
import com.noveo.android.internship.ridetogether.app.view.viewgroup.adapter.RouteAdapter;

import java.util.ArrayList;
import java.util.List;

public class RouteActivity extends BaseMvpActivity implements OnMapReadyCallback, RouteMvpView {
    @Bind(R.id.route_list)
    RecyclerView routeView;
    @Bind(R.id.route_toolbar)
    Toolbar toolbar;

    private RoutePresenter presenter = new RoutePresenter();;

    private GoogleMap map;
    private Route route;
    private int routeId;
    private Marker startMarker;
    private Marker endMarker;

    private List<RouteBased> items = new ArrayList<>();
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
        routeId = IntentUtil.getRouteId(getIntent());
        if (route != null) {
            presenter.loadComments(routeId);
            setUpMap();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (route != null) {
            startMarker = MapUtil.addMarker(startMarker, map, route, RouteUtil.getStartLatLng(route));
            endMarker = MapUtil.addMarker(endMarker, map, route, RouteUtil.getEndLatLng(route));
            GeoJsonLayer layer = MapUtil.addRouteToMap(map, route);
            MapUtil.animateCameraToRoute(map, layer);
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
    public void showComments(List<Comment> comments) {
        RouteUtil.addCommentsToItems(items, comments);
        if (items.size() > comments.size()) {
            adapter.notifyDataSetChanged();
        }
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
