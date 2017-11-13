package com.photogallery.app.domain.interactor;


import io.reactivex.observers.DisposableObserver;

public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}
