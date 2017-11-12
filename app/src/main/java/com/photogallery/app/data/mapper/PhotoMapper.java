package com.photogallery.app.data.mapper;

import com.photogallery.app.data.dto.PhotoDto;
import com.photogallery.app.data.model.Photo;

import java.util.List;
import java.util.stream.Collectors;


public class PhotoMapper {

    private static final int FIRST = 1;

    public static List<Photo> transfer(List<PhotoDto> photoEntities) {
        return photoEntities.stream().map(PhotoMapper::transfer).collect(Collectors.toList());
    }

    public static Photo transfer(PhotoDto photoEntity) {
        return Photo.builder()
                .smallImageUrl(photoEntity.getImageUrl())
                .bigImageUrl(photoEntity.getImages().get(FIRST).getUrl())
                .photoName(photoEntity.getName())
                .firstName(photoEntity.getUser().getFirstname())
                .lastName(photoEntity.getUser().getLastname())
                .cameraModel(photoEntity.getCamera())
                .build();
    }
}
