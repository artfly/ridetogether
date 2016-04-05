package com.noveo.android.internship.ridetogether.app.presentation.common;


import rx.Subscription;

public abstract class BasePresenter<V> {
    protected V view;
    protected Subscription subscription;

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
