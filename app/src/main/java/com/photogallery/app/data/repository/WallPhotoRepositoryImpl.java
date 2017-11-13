package com.photogallery.app.data.repository;


import com.photogallery.app.data.mapper.WallMapper;
import com.photogallery.app.data.model.Wall;
import com.photogallery.app.data.network.api.WallPhotoRestApiImpl;

import io.reactivex.Observable;


public class WallPhotoRepositoryImpl implements WallPhotoRepository {


    @Override
    public Observable<Wall> getWallPhotos(int page) {
        return WallPhotoRestApiImpl.getInstance().getWallPhotos(page).map(WallMapper::transfer);
    }

}
