package com.noveo.android.internship.ridetogether.app.callback;

import android.util.Log;
import com.noveo.android.internship.ridetogether.app.bus.BusProvider;
import com.noveo.android.internship.ridetogether.app.event.ReceiveRideEvent;
import com.noveo.android.internship.ridetogether.app.rest.model.Event;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventCallback implements Callback<Event> {

    private static final String LOG_TAG = EventCallback.class.getSimpleName();

    public EventCallback() {}

    @Override
    public void onResponse(Call<Event> call, Response<Event> response) {
        if (!response.isSuccessful()) {
            Log.d(LOG_TAG, "Get event error : " + response.errorBody());
            return;
        }
        BusProvider.getInstance().post(new ReceiveRideEvent(response.body()));
    }

    @Override
    public void onFailure(Call<Event> call, Throwable t) {
        Log.e(LOG_TAG, t.getMessage());
    }
}
