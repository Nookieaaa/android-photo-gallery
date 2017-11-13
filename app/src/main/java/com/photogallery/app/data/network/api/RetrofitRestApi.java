package com.photogallery.app.data.network.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.photogallery.app.data.dto.*;
import com.photogallery.app.data.network.Urls;

public interface RetrofitRestApi {

    @GET(Urls.WALL_PHOTOS)
    Observable<WallDto> getWallPhotos(@Query("page") int page);

}
