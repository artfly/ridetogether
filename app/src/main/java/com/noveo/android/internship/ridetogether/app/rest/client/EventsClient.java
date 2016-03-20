package com.noveo.android.internship.ridetogether.app.rest.client;

import com.google.gson.*;
import com.noveo.android.internship.ridetogether.app.rest.service.EventsService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by arty on 19.03.16.
 */
public class EventsClient {
    private static final String BASE_URL = "http://private-27d7ee-ridetogetherapi.apiary-mock.com/";
    private static EventsClient instance;
    private EventsService service;
    private Retrofit retrofit;

    private EventsClient() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        }).create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(EventsService.class);
    }

    public static EventsClient getInstance() {
        if (instance == null) {
            instance = new EventsClient();
            return instance;
        }
        return instance;
    }

    public EventsService getService() {
        return service;
    }
}
