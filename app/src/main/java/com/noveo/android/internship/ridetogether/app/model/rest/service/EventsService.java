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
    Observable<List<Event>> getEvents(@Query("place") String place, @Query("count") Integer count,
                                @Query("since") Long since, @Query("route") String routeType);

    @POST("events")
    Observable<Event> postEvent(@Header("Authorization") String authorization, @Body RequestEvent event);

    @DELETE("events/{eventId}")
    Observable<Void> deleteEvent(@Header("Authorization") String authorization, @Path("eventId") long eventId);

    @PUT("events/{eventId}")
    Observable<Event> updateEvent(@Header("Authorization") String authorization,
                                  @Path("eventId") int eventId, @Body Event event);

    @PUT("events/{eventId}/subscribe")
    Observable<Event> subscribeToEvent(@Header("Authorization") String authorization, @Path("eventId") int eventId);

    @DELETE("events/{eventId}/unsubscribe")
    Observable<Void> unsubscribeFromEvent(@Header("Authorization") String authorization, @Path("eventId") int eventId);
}
