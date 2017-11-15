package com.photogallery.app;

import android.app.Application;

import com.photogallery.app.data.database.Database;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class PhotoGalleryApplication extends Application {

    private static PhotoGalleryApplication instance;

    public static PhotoGalleryApplication getInstance() {
        return instance;
    }

    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public Observable<Database> observeDatabase(){
        if(database==null)
            database = Database.getDatabase(this);
        return Observable.just(database)
                .subscribeOn(Schedulers.single());
    }
}
