package com.noveo.android.internship.ridetogether.app.presentation.common;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseViewActivity extends BaseActivity implements BaseView {

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
