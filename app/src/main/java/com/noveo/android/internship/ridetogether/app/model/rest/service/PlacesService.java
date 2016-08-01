package com.noveo.android.internship.ridetogether.app.model.rest.service;

import com.noveo.android.internship.ridetogether.app.model.response.place.Places;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface PlacesService {

    @GET("maps/api/place/nearbysearch/json")
    Observable<Places> getPlaceId(@Query("location") String location, @Query("key") String key,
                                        @Query("radius") Integer radius);

}
