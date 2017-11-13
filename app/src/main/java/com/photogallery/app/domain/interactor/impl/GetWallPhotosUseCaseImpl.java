package com.photogallery.app.domain.interactor.impl;

import com.photogallery.app.data.model.Wall;
import com.photogallery.app.data.repository.WallPhotoRepository;
import com.photogallery.app.data.repository.WallPhotoRepositoryImpl;
import com.photogallery.app.domain.interactor.GetWallPhotosUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class GetWallPhotosUseCaseImpl implements GetWallPhotosUseCase {

    private WallPhotoRepository repository = new WallPhotoRepositoryImpl();

    @Override
    public Disposable execute(DisposableObserver<Wall> subscriber, int page) {
        return repository.getWallPhotos(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);
    }
}
