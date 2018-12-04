package com.abhijit.hubbletesting.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image  implements Serializable {

 private String id;

 private String thumbnailURL;

    public Image(String id, String thumbnailURL) {
        this.id = id;
        this.thumbnailURL = thumbnailURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
