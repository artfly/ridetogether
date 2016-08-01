package com.noveo.android.internship.ridetogether.app.presentation.main.event_creation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.ImagesService;
import com.noveo.android.internship.ridetogether.app.presentation.common.BasePresenter;
import com.noveo.android.internship.ridetogether.app.presentation.common.SchedulerTransformer;
import com.noveo.android.internship.ridetogether.app.utils.FileUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class EventCreationPresenter extends BasePresenter<EventCreationView> {
    private ImagesService imagesService = null;

    public void loadImage(Context context, Uri uri) {
        if (imagesService == null) {
            imagesService = RideTogetherClient.getInstance().getImageService();
        }
        File file = FileUtil.uriToFile(context, uri);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        imagesService.uploadImage(body)
                .compose(SchedulerTransformer.applySchedulers())
                .subscribe(image -> view.chooseRoute(image.getImagePath()));
    }
}
