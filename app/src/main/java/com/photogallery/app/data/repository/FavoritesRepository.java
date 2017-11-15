package com.photogallery.app.data.repository;


import com.photogallery.app.PhotoGalleryApplication;
import com.photogallery.app.data.database.Database;
import com.photogallery.app.data.database.dao.PhotoDao;
import com.photogallery.app.data.model.Photo;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public interface FavoritesRepository {
    default Flowable<List<Photo>> getPhotos() {
        return PhotoGalleryApplication.getInstance()
                .observeDatabase()
                .map(Database::photoDao)
                .toFlowable(BackpressureStrategy.LATEST)
                .flatMap(PhotoDao::getFavoritePhotos);
    }
}
