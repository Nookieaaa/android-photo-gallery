package com.photogallery.app.data.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDto {

    @SerializedName("size")
    private Integer size;
    @SerializedName("url")
    private String url;
    @SerializedName("https_url")
    private String httpsUrl;
    @SerializedName("format")
    private String format;

}
