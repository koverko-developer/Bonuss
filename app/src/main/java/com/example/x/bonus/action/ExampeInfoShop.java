package com.example.x.bonus.action;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mobi app on 01.08.2017.
 */

public class ExampeInfoShop {

    @SerializedName("info")
    @Expose
    private List<Info> info = null;

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

}
