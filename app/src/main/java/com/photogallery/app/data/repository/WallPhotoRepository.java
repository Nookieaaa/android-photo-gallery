package com.photogallery.app.data.repository;

import com.photogallery.app.data.model.Wall;

import rx.Observable;

public interface WallPhotoRepository {
    Observable<Wall> getWallPhotos(int page);
}
