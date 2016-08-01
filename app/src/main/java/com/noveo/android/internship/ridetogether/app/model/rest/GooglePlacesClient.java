package com.noveo.android.internship.ridetogether.app.model.rest;

import com.noveo.android.internship.ridetogether.app.model.rest.service.PlacesService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GooglePlacesClient {
    private String BASE_URL = "https://maps.googleapis.com";
    private static final GooglePlacesClient instance = new GooglePlacesClient();
    private PlacesService placesService;

    private GooglePlacesClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        placesService = retrofit.create(PlacesService.class);
    }

    public static GooglePlacesClient getInstance() {
        return instance;
    }

    public PlacesService getPlacesService() {
        return placesService;
    }
}
