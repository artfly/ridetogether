package com.noveo.android.internship.ridetogether.app.presentation.main.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.noveo.android.internship.ridetogether.app.model.rest.RideTogetherClient;
import com.noveo.android.internship.ridetogether.app.model.rest.service.UsersService;
import com.noveo.android.internship.ridetogether.app.presentation.common.BasePresenter;
import com.noveo.android.internship.ridetogether.app.presentation.common.SchedulerTransformer;
import com.noveo.android.internship.ridetogether.app.utils.LoginUtil;

import java.util.concurrent.ExecutionException;

public class LoginPresenter extends BasePresenter<LoginView> {
    private UsersService usersService;

    public void loginUser(String username, String password) {
        if (usersService == null) {
            usersService = RideTogetherClient.getInstance().getUsersService();
        }
        String basicAuth = LoginUtil.createAuthString(username, password);
        subscription = usersService.loginUser(basicAuth)
                .compose(SchedulerTransformer.applySchedulers())
                .onErrorReturn(throwable -> null)
                .subscribe(user -> view.onResult(user));
    }

    public void loadImage(Context context, String imagePath) {
        Log.d("Glider", RideTogetherClient.getImageUrl(imagePath));
        Glide.with(context)
                .load(RideTogetherClient.getImageUrl(imagePath))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        LoginUtil.storeUserImage(context, resource);
                        view.showEvents();
                    }
                });
    }
}
