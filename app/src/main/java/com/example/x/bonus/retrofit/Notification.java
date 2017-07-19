package com.example.x.bonus.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mobi app on 06.07.2017.
 */

public class Notification {

    @SerializedName("notice_all")
    @Expose
    private List<List<List<NoticeAll>>> noticeAll = null;

    public List<List<List<NoticeAll>>> getNoticeAll() {
        return noticeAll;
    }

    public void setNoticeAll(List<List<List<NoticeAll>>> noticeAll) {
        this.noticeAll = noticeAll;
    }

}
