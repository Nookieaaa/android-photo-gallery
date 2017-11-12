package com.photogallery.app.data.network.api;

import com.photogallery.app.data.dto.WallDto;
import com.photogallery.app.data.network.Urls;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


public class WallPhotoRestApiImpl implements WallPhotoRestApi {

    private static WallPhotoRestApi instance;
    private RetrofitRestApi retrofitRestApi;

    private WallPhotoRestApiImpl() {
        retrofitRestApi = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(RetrofitRestApi.class);
    }

    public static WallPhotoRestApi getInstance() {
        if (instance == null)
            instance = new WallPhotoRestApiImpl();
        return instance;
    }

    @Override
    public Observable<WallDto> getWallPhotos(int page) {
        return retrofitRestApi.getWallPhotos(page);
    }
}
