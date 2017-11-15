package com.photogallery.app.presentation.ui.view.activity.favorites;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.photogallery.app.PhotoGalleryApplication;
import com.photogallery.app.R;
import com.photogallery.app.data.database.Database;
import com.photogallery.app.data.database.dao.PhotoDao;
import com.photogallery.app.data.model.Photo;
import com.photogallery.app.presentation.ui.adapter.FavoritesAdapter;
import com.photogallery.app.presentation.ui.adapter.OnPhotoClickListener;
import com.photogallery.app.presentation.ui.view.activity.base.BaseActivity;
import com.photogallery.app.presentation.ui.view.activity.photo.PhotoActivity;

import java.util.List;

import butterknife.BindView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class FavoritesActivity extends BaseActivity implements OnPhotoClickListener {

    private FavoritesAdapter adapter;

    @BindView(R.id.rv_wall_content)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle(R.string.favorites);

        adapter = new FavoritesAdapter(this);
        GridLayoutManager glm = new GridLayoutManager(this,
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                        ? 2 : 3);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapter);
        PhotoGalleryApplication.getInstance()
                .observeDatabase()
                .map(Database::photoDao)
                .toFlowable(BackpressureStrategy.LATEST)
                .flatMap(PhotoDao::getFavoritePhotos)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::displayPhotos)
                .subscribe(__->{
                    Log.d("Favorites", "adapter populated");
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_favorites;
    }

    public void displayPhotos(List<Photo> data) {
        adapter.setData(data);
    }

    @Override
    public void onPhotoClick(Photo photo) {
        startActivity(PhotoActivity.getStartIntent(this, photo));
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, FavoritesActivity.class);
        return intent;
    }
}
