package com.photogallery.app.data.mapper;

import com.photogallery.app.data.dto.WallDto;
import com.photogallery.app.data.model.Wall;

import io.reactivex.Observable;

public class WallMapper {

    public static Observable<Wall> transfer(WallDto wallDto){

        return Observable.just(Wall.builder()
                .currentPage(wallDto.getCurrentPage())
                .totalPages(wallDto.getTotalPages())
                .totalItems(wallDto.getTotalItems()))
                .zipWith(PhotoMapper.transfer(wallDto.getPhotos()), (wallBuilder, photos) -> wallBuilder.photos(photos).build());
    }
}
