package com.noveo.android.internship.ridetogether.app.presentation.main.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseViewActivity;
import com.noveo.android.internship.ridetogether.app.presentation.main.events.EventListView;
import com.noveo.android.internship.ridetogether.app.presentation.main.events.EventsPresenter;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;

import java.io.IOException;
import java.util.List;

public class SplashScreenActivity extends BaseViewActivity implements EventListView, ConnectionCallbacks, OnConnectionFailedListener {
    private EventsPresenter presenter = new EventsPresenter();
    private GoogleApiClient googleApiClient;
    private Address currentAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
        setContentView(R.layout.splash_screen);

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showEvents(List<Event> events) {
        startActivity(IntentUtil.createIntent(this, events, currentAddress));
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void attachPresenter() {
        presenter.attachView(this);
    }

    @Override
    public void detachPresenter() {
        presenter.detachView();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> addresses = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 100);
                for (Address address : addresses) {
                    if (address.getThoroughfare() == null) {
                        currentAddress = address;
                        break;
                    }
                }

                if (currentAddress != null) {
                    presenter.getPlaceId(currentAddress.getLatitude(), currentAddress.getLongitude(),
                            getResources().getString(R.string.google_places_key));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
