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
public class WallDto {

    @SerializedName("current_page")
    private Integer currentPage;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_items")
    private Integer totalItems;
    @SerializedName("photos")
    private List<PhotoDto> photos = new ArrayList<>();


}
