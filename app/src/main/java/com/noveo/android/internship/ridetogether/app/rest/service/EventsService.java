package com.noveo.android.internship.ridetogether.app.rest.service;

import com.noveo.android.internship.ridetogether.app.rest.model.Event;
import com.noveo.android.internship.ridetogether.app.rest.model.RequestEvent;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface EventsService {

    @GET("events/{event_id}")
    Call<Event> getEvent(@Path("event_id") int eventId);

    @GET("events")
    Call<List<Event>> getEvents(@Query("count") Integer count,
                                @Query("since") Integer since, @Query("region") Integer region);

    @POST("events")
    Call<Event> postEvent(@Header("Token") String token, @Body RequestEvent event);

    @DELETE("events/{event_id}")
    Call<Void> deleteEvent(@Path("event_id") int eventId, @Header("Token") String token);

    @PUT("events/{event_id}")
    Call<Event> updateEvent(@Path("event_id") int eventId,
                            @Header("Token") String token, @Body Event event);

    @PUT("events/{event_id}/subscribe")
    Call<Event> subscribeToEvent(@Path("event_id") int eventId, @Header("Token") String token);

    @DELETE("events/{event_id}/unsubscribe")
    Call<Void> unsubscribeFromEvent(@Path("event_id") int eventId, @Header("Token") String token);
}
