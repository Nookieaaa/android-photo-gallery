package com.photogallery.app.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Photo implements Serializable{
    private String smallImageUrl;
    @PrimaryKey
    @NonNull
    private String bigImageUrl;
    private String photoName;
    private String firstName;
    private String lastName;
    private String cameraModel;
    String localPath;

}
