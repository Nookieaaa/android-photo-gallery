package com.photogallery.app.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDto {

    @SerializedName("camera")
    private String camera;
    @SerializedName("name")
    private String name;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("images")
    private List<ImageDto> images = new ArrayList<>();
    @SerializedName("user")
    private UserDto user;


}
