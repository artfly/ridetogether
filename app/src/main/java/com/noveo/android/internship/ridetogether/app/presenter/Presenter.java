package com.noveo.android.internship.ridetogether.app.presenter;


public interface Presenter<V> {

    void attachView(V v);

    void detachView();
}
