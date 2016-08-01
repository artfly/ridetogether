package com.noveo.android.internship.ridetogether.app.presentation.main.route_creation;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import butterknife.OnClick;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.request.RequestEvent;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseViewActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.home.SplashScreenActivity;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;
import com.noveo.android.internship.ridetogether.app.utils.MapUtil;
import com.noveo.android.internship.ridetogether.app.utils.RouteUtil;

import java.util.ArrayList;
import java.util.List;

public class RouteCreationMapActivity extends BaseViewActivity implements
        RouteCreationView, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    private RouteCreationPresenter routeCreationPresenter = new RouteCreationPresenter();
    private GoogleMap map;
    private LatLng currentPosition;
    private List<LatLng> markers = new ArrayList<>();
    private List<Polyline> lines = new ArrayList<>();

    @OnClick(R.id.submit_route_map_fab)
    public void submitRoute() {
        if (markers.size() < 2) {
            Toast.makeText(this, "Not enough markers!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = getIntent();
        routeCreationPresenter.addRoute(this, IntentUtil.getProperties(intent), markers);
        startActivity(new Intent(this, SplashScreenActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_route_creation_map);

        currentPosition = IntentUtil.getCurrentPosition(getIntent());
        setUpMap();
    }

    @Override
    public void attachPresenter() {
        routeCreationPresenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        routeCreationPresenter.detachView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
        MapUtil.animateCameraToPosition(map, currentPosition);
    }

    private void  setUpMap() {
        if (map == null) {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMapAsync(this);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MapUtil.addMarker(map, latLng);
        if (markers.size() > 0) {
            lines.add(MapUtil.addPolyline(map, markers.get(markers.size() - 1), latLng));
        }
        markers.add(latLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int linePosition = markers.indexOf(marker.getPosition()) - 1;
        if (linePosition >= 0) {
            lines.get(linePosition).remove();
            lines.remove(linePosition);
        }
        markers.remove(marker.getPosition());
        marker.remove();
        return true;
    }

    @Override
    public void onCreation() {
        startActivity(new Intent(this, SplashScreenActivity.class));
    }

    @Override
    public void addEvent(long routeId) {
        RequestEvent event = IntentUtil.getRequestEvent(getIntent());
        event.setRouteId(routeId);
        routeCreationPresenter.postEvent(this, event);
    }
}
