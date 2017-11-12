
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
public class UserDto {
    @SerializedName("username")
    private String username;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;

}
