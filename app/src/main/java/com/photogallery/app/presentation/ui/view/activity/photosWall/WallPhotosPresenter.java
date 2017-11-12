package com.photogallery.app.presentation.ui.view.activity.photosWall;

import com.photogallery.app.presentation.ui.view.activity.base.BasePresenter;

public interface WallPhotosPresenter extends BasePresenter<WallView> {

    boolean isLoading();

    int getCurrentPage();

    int getTotalPages();

    void loadFirstPage();

    void loadNextPage();

}
