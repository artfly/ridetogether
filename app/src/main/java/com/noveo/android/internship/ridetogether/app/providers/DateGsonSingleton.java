package com.noveo.android.internship.ridetogether.app.providers;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;


public enum DateGsonSingleton {
    INSTANCE;

    private final Gson gson;

    DateGsonSingleton() {
        gson = buildGson();
    }

    public static DateGsonSingleton getInstance() {
        return INSTANCE;
    }

    private Gson buildGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(java.util.Date.class, new DateToJsonAdapter());
        return builder.create();
    }

    public Gson getGson() {
        return gson;
    }

    private class DateToJsonAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new java.util.Date(json.getAsLong() * 1000);
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime() / 1000);
        }
    }
}
