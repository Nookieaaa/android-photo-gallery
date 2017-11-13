package com.photogallery.app.domain.interactor;


import com.photogallery.app.data.model.Wall;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public interface GetWallPhotosUseCase {

    Disposable execute(DisposableObserver<Wall> subscriber, int page);

}
