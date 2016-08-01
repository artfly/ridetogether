package com.noveo.android.internship.ridetogether.app.model.rest.service;

import com.noveo.android.internship.ridetogether.app.model.request.RequestComment;
import com.noveo.android.internship.ridetogether.app.model.request.RequestRoute;
import com.noveo.android.internship.ridetogether.app.model.response.route.Comment;
import com.noveo.android.internship.ridetogether.app.model.response.route.Route;
import retrofit2.Call;
import retrofit2.http.*;
import rx.Observable;

import java.util.List;

public interface RoutesService {

    @POST("routes")
    Observable<Route> postRoute(@Header("Authorization") String authorization, @Body RequestRoute event);

    @GET("routes")
    Observable<List<Route>> getRoutes(@Query("count") Integer count, @Query("place") String place,
                                      @Query("type") String routeType, @Query("since") Long since);

    @GET("routes/{route_id}")
    Observable<Route> getRoute(@Path("route_id") int routeId);

    @DELETE("routes/{route_id}")
    Observable<Void> deleteRoute(@Header("Authorization") String authorization, @Path("route_id") int routeId);

    @GET("routes/{route_id}/comments")
    Observable<List<Comment>> getComments(@Path("route_id") int routeId, @Query("count") Integer count,
                                          @Query("since") Long since);

    @POST("routes/{route_id}/comments")
    Observable<Comment> postComment(@Header("Authorization") String authorization,
                              @Path("route_id") int routeId, @Body RequestComment comment);

    @DELETE("routes/{route_id}/comments/{comment_id}")
    Observable<Void> deleteComment(@Header("Authorization") String authorization,
                             @Path("route_id") int routeId, @Path("comment_id") int commentId);
}
