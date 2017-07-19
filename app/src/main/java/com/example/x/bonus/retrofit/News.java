package com.example.x.bonus.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by x on 22.06.2017.
 */

public class News {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("title")
    @Expose
    private String title;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}