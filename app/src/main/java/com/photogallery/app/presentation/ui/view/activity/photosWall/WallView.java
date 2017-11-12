package com.photogallery.app.presentation.ui.view.activity.photosWall;

import com.photogallery.app.data.model.Wall;
import com.photogallery.app.presentation.ui.view.activity.base.BaseView;
import com.photogallery.app.presentation.ui.view.activity.base.LoadingView;


public interface WallView extends BaseView, LoadingView {

    void showReload();

    void hideReload();

    void reloadWallPhotos();

    void updateWallPhotos(Wall wall);

    void initWallPhotosAdapter();
}
