package com.photogallery.app.domain.interactor;


import com.photogallery.app.data.model.Wall;

import rx.Subscriber;

public interface GetWallPhotosUseCase {

    void execute(Subscriber<Wall> subscriber, int page);

    void unSubscribe();
}
