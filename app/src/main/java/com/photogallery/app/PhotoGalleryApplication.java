package com.photogallery.app;

import android.app.Application;


public class PhotoGalleryApplication extends Application {

    private static PhotoGalleryApplication instance;

    public static PhotoGalleryApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


}
