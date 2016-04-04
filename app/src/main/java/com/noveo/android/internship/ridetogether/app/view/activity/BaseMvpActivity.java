package com.noveo.android.internship.ridetogether.app.view.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseMvpActivity extends BaseActivity implements MvpView {

    public abstract void attachPresenter();

    public abstract void detachPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachPresenter();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
