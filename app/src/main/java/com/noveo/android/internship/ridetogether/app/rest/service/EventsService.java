package com.noveo.android.internship.ridetogether.app.rest.service;

import com.noveo.android.internship.ridetogether.app.rest.model.Event;
import com.noveo.android.internship.ridetogether.app.rest.model.RequestEvent;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface EventsService {

    @GET("events/{eventId}")
    Call<Event> getEvent(@Path("eventId") int eventId);

    @GET("events")
    Call<List<Event>> getEvents(@Query("count") Integer count,
                                @Query("since") Integer since, @Query("region") Integer region);

    @POST("events")
    Call<Event> postEvent(@Header("Token") String token, @Body RequestEvent event);

    @DELETE("events/{eventId}")
    Call<Void> deleteEvent(@Path("eventId") int eventId, @Header("Token") String token);

    @PUT("events/{eventId}")
    Call<Event> updateEvent(@Path("eventId") int eventId,
                            @Header("Token") String token, @Body Event event);

    @PUT("events/{eventId}/subscribe")
    Call<Event> subscribeToEvent(@Path("eventId") int eventId, @Header("Token") String token);

    @DELETE("events/{eventId}/unsubscribe")
    Call<Void> unsubscribeFromEvent(@Path("eventId") int eventId, @Header("Token") String token);
}
