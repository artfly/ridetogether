package com.noveo.android.internship.ridetogether.app.presentation.common;


import android.content.Context;

public interface BaseView {
    Context getContext();

    void attachPresenter();

    void detachPresenter();
}
