package com.noveo.android.internship.ridetogether.app.model.rest.service;


import com.noveo.android.internship.ridetogether.app.model.response.User;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import rx.Observable;

public interface UsersService {

    @GET("users/login")
    Observable<User> loginUser(@Header("Authorization") String authorization);
}
