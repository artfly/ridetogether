package com.noveo.android.internship.ridetogether.app.callback;

import android.util.Log;
import com.noveo.android.internship.ridetogether.app.bus.BusProvider;
import com.noveo.android.internship.ridetogether.app.event.ReceiveUnsubscribeEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arty on 20.03.16.
 */
public class UnsubscribeCallback implements Callback<Void> {

    private static final String LOG_TAG = EventCallback.class.getSimpleName();

    public UnsubscribeCallback() {}

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (!response.isSuccessful()) {
            Log.d(LOG_TAG, "Get event error : " + response.errorBody());
            return;
        }
        BusProvider.getInstance().post(new ReceiveUnsubscribeEvent());
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Log.e(LOG_TAG, t.getMessage());
    }

}
