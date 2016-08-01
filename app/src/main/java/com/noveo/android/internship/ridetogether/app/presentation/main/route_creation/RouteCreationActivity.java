package com.noveo.android.internship.ridetogether.app.presentation.main.route_creation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.noveo.android.internship.ridetogether.app.R;
import com.noveo.android.internship.ridetogether.app.model.request.RequestEvent;
import com.noveo.android.internship.ridetogether.app.model.request.RequestProperties;
import com.noveo.android.internship.ridetogether.app.presentation.common.BaseViewActivity;
import com.noveo.android.internship.ridetogether.app.utils.IntentUtil;

public class RouteCreationActivity extends BaseViewActivity implements PlaceSelectionListener {
    @Bind(R.id.edit_description)
    EditText editDescription;
    @Bind(R.id.edit_title)
    EditText editTitle;
    @Bind(R.id.activity_route_creation)
    View content;
    private PlaceAutocompleteFragment autocompleteFragment;
    private Place place;
    private int which;
    private String routeType;

    @OnClick(R.id.button_route_type)
    void showRouteTypeDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.route_type))
                .setSingleChoiceItems(getResources().getStringArray(R.array.route_types), which,
                        (dialog, which) -> {
                            this.which = which;
                            routeType = getResources().getStringArray(R.array.route_types)[which];
                            dialog.dismiss();
                        })
                .show();
    }

    @OnClick(R.id.submit_route_fab)
    void submitRoute() {
        if (TextUtils.isEmpty(editDescription.getText()) ||
                TextUtils.isEmpty(editTitle.getText()) || place == null || routeType == null) {
            Snackbar snackbar = Snackbar.make(content, "Please fill in all fields", Snackbar.LENGTH_SHORT);
            ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setGravity(Gravity.CENTER_HORIZONTAL);
            snackbar.show();
        } else {
            RequestEvent event = IntentUtil.getRequestEvent(getIntent());
            RequestProperties properties = new RequestProperties(editTitle.getText().toString(), place.getId(),
                    editDescription.getText().toString(), routeType);
            startActivity(IntentUtil.createIntent(this, properties, event, place.getLatLng()));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_route_creation);

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHint(getResources().getString(R.string.location));
        autocompleteFragment.setFilter(
                new AutocompleteFilter.Builder()
                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                        .build());
    }

    @Override
    public void onPlaceSelected(Place place) {
        for (Integer type : place.getPlaceTypes()) {
            Log.d("PLACES", type.toString());
            if (type == Place.TYPE_SUBLOCALITY_LEVEL_2 || type == Place.TYPE_LOCALITY) {
                this.place = place;
                return;
            }
        }
        Snackbar snackbar = Snackbar.make(content, "Please select city or its sublocality", Snackbar.LENGTH_SHORT);
        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setGravity(Gravity.CENTER_HORIZONTAL);
        snackbar.show();
    }

    @Override
    public void onError(Status status) {

    }

    @Override
    public void attachPresenter() {

    }

    @Override
    public void detachPresenter() {

    }
}
