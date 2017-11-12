package com.photogallery.app.domain.interactor.impl;

import com.photogallery.app.data.repository.WallPhotoRepository;
import com.photogallery.app.data.repository.WallPhotoRepositoryImpl;
import com.photogallery.app.domain.interactor.GetWallPhotosUseCase;
import com.photogallery.app.data.model.Wall;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;
import rx.schedulers.Schedulers;


public class GetWallPhotosUseCaseImpl implements GetWallPhotosUseCase {

    private WallPhotoRepository repository = new WallPhotoRepositoryImpl();
    private Subscription subscription = Subscribers.empty();

    @Override
    public void execute(Subscriber<Wall> subscriber, int page) {
        subscription = repository.getWallPhotos(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void unSubscribe() {
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
