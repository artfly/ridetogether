package com.noveo.android.internship.ridetogether.app.model.rest.service;

import com.noveo.android.internship.ridetogether.app.model.response.event.Event;
import com.noveo.android.internship.ridetogether.app.model.request.RequestEvent;
import retrofit2.Call;
import retrofit2.http.*;
import rx.Observable;

import java.util.List;

public interface EventsService {

    @GET("events/{eventId}")
    Observable<Event> getEvent(@Path("eventId") int eventId);

    @GET("events")
    Observable<List<Event>> getEvents(@Query("count") Integer count,
                                @Query("since") Long since, @Query("region") Integer region);

    @POST("events")
    Observable<Event> postEvent(@Header("Token") String token, @Body RequestEvent event);

    @DELETE("events/{eventId}")
    Observable<Void> deleteEvent(@Path("eventId") int eventId, @Header("Token") String token);

    @PUT("events/{eventId}")
    Observable<Event> updateEvent(@Path("eventId") int eventId,
                            @Header("Token") String token, @Body Event event);

    @PUT("events/{eventId}/subscribe")
    Observable<Event> subscribeToEvent(@Path("eventId") int eventId, @Header("Token") String token);

    @DELETE("events/{eventId}/unsubscribe")
    Observable<Void> unsubscribeFromEvent(@Path("eventId") int eventId, @Header("Token") String token);
}
