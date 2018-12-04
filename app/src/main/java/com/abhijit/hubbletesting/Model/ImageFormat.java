package com.abhijit.hubbletesting.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageFormat {
    public ImageFormat() {
    }

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    private  String mission;

    private String credits;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public List<image_files> getImage_files() {
        return mImage_files;
    }

    public void setImage_files(List<image_files> image_files) {
        mImage_files = image_files;
    }

    @SerializedName("image_files")

    private List<image_files> mImage_files;

}
