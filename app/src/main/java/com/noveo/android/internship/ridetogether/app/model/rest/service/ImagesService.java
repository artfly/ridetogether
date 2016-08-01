package com.noveo.android.internship.ridetogether.app.model.rest.service;

import com.noveo.android.internship.ridetogether.app.model.response.Image;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface ImagesService {
    @Multipart
    @POST("pics")
    Observable<Image> uploadImage(@Part MultipartBody.Part file);
}
