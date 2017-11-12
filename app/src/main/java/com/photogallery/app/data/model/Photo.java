package com.photogallery.app.data.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo implements Serializable{
    private String smallImageUrl;
    private String bigImageUrl;
    private String photoName;
    private String firstName;
    private String lastName;
    private String cameraModel;

}
