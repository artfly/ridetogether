package com.noveo.android.internship.ridetogether.app.model.rest;

import com.noveo.android.internship.ridetogether.app.model.rest.service.EventsService;
import com.noveo.android.internship.ridetogether.app.model.rest.service.RoutesService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RideTogetherClient {
    private static final String BASE_URL = "http://private-27d7ee-ridetogetherapi.apiary-mock.com/";
    private static final RideTogetherClient instance = new RideTogetherClient();
    private EventsService eventsService;
    private RoutesService routesService;

    private RideTogetherClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(DateGsonSingleton.INSTANCE.getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        eventsService = retrofit.create(EventsService.class);
        routesService = retrofit.create(RoutesService.class);
    }

    public static RideTogetherClient getInstance() {
        return instance;
    }

    public EventsService getEventsService() {
        return eventsService;
    }

    public RoutesService getRoutesService() {
        return routesService;
    }
}
