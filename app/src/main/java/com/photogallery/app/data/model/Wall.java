package com.photogallery.app.data.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wall {

    private int totalPages;
    private int totalItems;
    private int currentPage;
    private List<Photo> photos = new ArrayList<>();

    public void add(Wall wall) {
        photos.addAll(wall.getPhotos());
        currentPage = wall.getCurrentPage();
        totalPages = wall.getTotalPages();
    }

    public void reset() {
        photos.clear();
        totalPages = 1;
        currentPage = 0;
    }
}
