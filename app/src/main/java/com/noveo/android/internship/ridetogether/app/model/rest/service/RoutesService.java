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
    Observable<Route> postRoute(@Header("Token") String token, @Body RequestRoute event);

    @DELETE("routes/{route_id}")
    Observable<Void> deleteRoute(@Header("Token") String token, @Path("route_id") int routeId);

    @GET("routes")
    Observable<List<Route>> getRoutes(@Query("count") Integer count,
                                @Query("offset") Integer offset, @Query("sort") String sortOrder);

    @GET("routes/{route_id}")
    Observable<Route> getRoute(@Path("route_id") int routeId);

    @POST("routes/{route_id}/comments")
    Observable<Comment> postComment(@Header("Token") String token,
                              @Path("route_id") int routeId, @Body RequestComment comment);

    @DELETE("routes/{route_id}/comments/{comment_id}")
    Observable<Void> deleteComment(@Header("Token") String token,
                             @Path("route_id") int routeId, @Path("comment_id") int commentId);

    @GET("routes/{route_id}/comments")
    Observable<List<Comment>> getComments(@Path("route_id") int routeId, @Query("count") Integer count,
                                          @Query("offset") Integer offset, @Query("sort") String sortOrder);
}
