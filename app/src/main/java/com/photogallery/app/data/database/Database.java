package com.photogallery.app.data.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.photogallery.app.data.database.dao.PhotoDao;
import com.photogallery.app.data.model.Photo;

@android.arch.persistence.room.Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "Favorite_photos";
    private static Database INSTANCE;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            Log.d("Database", "migration stub");
        }
    };

    public abstract PhotoDao photoDao();

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context, Database.class, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
