package com.photogallery.app.data.mapper;

import com.photogallery.app.data.dto.WallDto;
import com.photogallery.app.data.model.Wall;

public class WallMapper {

    public static Wall transfer(WallDto wallDto){

        return Wall.builder()
                .currentPage(wallDto.getCurrentPage())
                .totalPages(wallDto.getTotalPages())
                .totalItems(wallDto.getTotalItems())
                .photos(PhotoMapper.transfer(wallDto.getPhotos()))
                .build();
    }
}
