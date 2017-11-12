package com.photogallery.app.presentation.ui.view.activity.base;

public interface BasePresenter<T> {

    void setView(T t);

    void onStop();
}
