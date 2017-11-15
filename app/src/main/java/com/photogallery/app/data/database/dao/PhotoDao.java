package com.photogallery.app.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.photogallery.app.data.model.Photo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPhoto(Photo photo);

    @Query("SELECT * FROM photo")
    Flowable<List<Photo>> getFavoritePhotos();

    @Query("SELECT * FROM photo WHERE bigImageUrl = :bigImageUrl")
    Maybe<Photo> getPhotoByImageUrl(String bigImageUrl);

    @Delete
    void deletePhoto(Photo photo);
}
