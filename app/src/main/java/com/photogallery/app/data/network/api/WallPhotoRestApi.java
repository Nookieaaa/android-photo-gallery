package com.photogallery.app.data.network.api;
import com.photogallery.app.data.dto.WallDto;
import io.reactivex.Observable;

public interface WallPhotoRestApi {
    Observable<WallDto> getWallPhotos(int page);
}
