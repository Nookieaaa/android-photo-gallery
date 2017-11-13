package com.photogallery.app.data.network.api;

import com.photogallery.app.data.dto.WallDto;
import com.photogallery.app.data.network.Urls;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class WallPhotoRestApiImpl implements WallPhotoRestApi {

    private static WallPhotoRestApi instance;
    private RetrofitRestApi retrofitRestApi;

    private WallPhotoRestApiImpl() {
        retrofitRestApi = new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RetrofitRestApi.class);
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build();
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
