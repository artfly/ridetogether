package com.noveo.android.internship.ridetogether.app.presentation.common;


import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseViewFragment extends BaseFragment implements BaseView {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachPresenter();
    }
}
