package com.photogallery.app.presentation.ui.view.activity.photo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.photogallery.app.PhotoGalleryApplication;
import com.photogallery.app.R;
import com.photogallery.app.data.database.Database;
import com.photogallery.app.data.model.Photo;
import com.photogallery.app.presentation.ui.view.activity.base.BaseActivity;
import com.photogallery.app.presentation.util.ShareUtil;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PhotoActivity extends BaseActivity {

    public static final String EXTRA_PHOTO = "extra_photo";
    private static final String ARG_FAVORITE_STATE = "favorite";

    @BindView(R.id.iv_photo)
    ImageViewTouch ivPhoto;
    @BindView(R.id.pw_photo)
    ProgressWheel pwPhoto;
    @BindView(R.id.tv_user_name)
    AppCompatTextView tvUserName;
    @BindView(R.id.bottomsheet)
    BottomSheetLayout bottomSheet;

    Photo photo;

    MenuItem favoriteMenu;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    //runtime value
    private boolean isFavorite;
    //initial value
    private boolean isFavoriteInitial;

    private Bitmap image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState != null) {
            isFavorite = savedInstanceState.getBoolean(ARG_FAVORITE_STATE, false);
        }
        loadPhotoAndUserInfo();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_photo;
    }

    private void loadPhotoAndUserInfo() {
        photo = (Photo) getIntent().getSerializableExtra(EXTRA_PHOTO);
        setUserInfo(photo);
        checkIsFavorite(photo);
    }

    private void loadPhoto(String source) {
        pwPhoto.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(source)
                .asBitmap()
                .fitCenter()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        image = resource;
                        pwPhoto.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(ivPhoto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_menu, menu);
        favoriteMenu = menu.findItem(R.id.favorite);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite: {
                PhotoActivityPermissionsDispatcher.setFavoriteWithPermissionCheck(PhotoActivity.this, !item.isChecked());
                return true;
            }
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (favoriteMenu != null)
            setFavorite(isFavorite);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_FAVORITE_STATE, isFavorite);
    }

    private void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //apply db operation only if needed
        if (isFavoriteInitial != isFavorite)
            saveFavoriteChanges();
    }

    private void setUserInfo(Photo photo) {
        String photoTittle = photo.getPhotoName() != null ? photo.getPhotoName() : getString(R.string.app_name);
        String firstName = photo.getFirstName();
        String lastName = photo.getLastName();
        String cameraModel = photo.getCameraModel();

        String firstNameLastName = getString(R.string.textView_firstName_lastName, firstName, lastName);
        if (cameraModel != null)
            tvUserName.setText(getString(R.string.text_view_userName_cameraModel, firstNameLastName, cameraModel));
        else
            tvUserName.setText(firstNameLastName);
        getSupportActionBar().setTitle(photoTittle);
    }

    @OnClick(R.id.fab_share)
    public void shareImageUrl() {
        Photo photo = (Photo) getIntent().getSerializableExtra(EXTRA_PHOTO);
        ShareUtil.shareImage(this, bottomSheet, photo);
    }

    private void saveFavoriteChanges() {
        if (!isFavorite)
            PhotoActivityPermissionsDispatcher
                    .deletePhotoFileWithPermissionCheck(PhotoActivity.this, photo.getLocalPath());
        else {
            String fileName = String.valueOf(new Date().getTime()) + ".jpg";
            if (photo.getLocalPath() != null) {
                File f = new File(photo.getLocalPath());
                if (f.exists()) {
                    fileName = f.getName();
                }
            }
            PhotoActivityPermissionsDispatcher.saveToFileWithPermissionCheck(PhotoActivity.this, image, fileName);
        }
    }

    private void checkIsFavorite(Photo photo) {
        add(
                PhotoGalleryApplication.getInstance()
                        .observeDatabase()
                        .flatMapMaybe(database ->
                                database.photoDao()
                                        .getPhotoByImageUrl(photo.getBigImageUrl())
                                        .doOnSuccess(found -> {
                                            photo.setLocalPath(found.getLocalPath());
                                        })
                                        .map(found -> true)
                                        .defaultIfEmpty(false)
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                                    isFavoriteInitial = result;
                                    if (result)
                                        PhotoActivityPermissionsDispatcher
                                                .setFavoriteWithPermissionCheck(PhotoActivity.this, true);
                                    Log.d("Database", "Photo is favorite " + String.valueOf(result));
                                    loadPhoto(result && photo.getLocalPath() != null ? photo.getLocalPath() : photo.getBigImageUrl());
                                },
                                Throwable::printStackTrace)
        );
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void setFavorite(boolean value) {
        isFavorite = value;
        if (favoriteMenu != null) {
            favoriteMenu.setChecked(value);
            favoriteMenu.setIcon(value ? android.R.drawable.star_on : android.R.drawable.star_off);
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void saveToFile(Bitmap bitmap, String name) {
        Single.just(photo)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(photo -> {
                    File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File dir = new File(root, "PhotoGallery");
                    if (!dir.exists())
                        dir.mkdirs();
                    OutputStream out = null;
                    String filePath = null;
                    try {
                        File file = new File(dir, name);
                        out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.close();
                        filePath = file.getAbsolutePath();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photo.setLocalPath(filePath);

                })
                .subscribe(__ -> {
                    commitChangesToDatabase();
                }, Throwable::printStackTrace);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void deletePhotoFile(String path) {
        if (path == null)
            return;
        Single.just(path)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(s -> {
                    File f = new File(s);
                    boolean deleted = false;
                    if (f.exists())
                        deleted = f.delete();
                    Log.d("Files", "File " + f.getAbsolutePath() + " deleted " + String.valueOf(deleted));
                })
                .subscribe(__ -> {
                    commitChangesToDatabase();
                }, Throwable::printStackTrace);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void commitChangesToDatabase() {
        PhotoGalleryApplication.getInstance()
                .observeDatabase()
                .map(Database::photoDao)
                .map(photoDao -> {
                    if (isFavorite) {
                        photoDao.addPhoto(photo);
                    } else {
                        photoDao.deletePhoto(photo);
                    }
                    Log.d("Database", "Entry "
                            + photo.getBigImageUrl()
                            + (isFavorite ? " added to favorites" : " deleted from favorites"));
                    return isFavorite;
                })
                .subscribe(__ -> {
                }, err -> {
                    err.printStackTrace();
//                    compositeDisposable.clear();
                }, () -> {
//                    compositeDisposable.clear();
                });
    }

    public static Intent getStartIntent(Context context, Photo photo) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(PhotoActivity.EXTRA_PHOTO, photo);
        return intent;
    }
}
