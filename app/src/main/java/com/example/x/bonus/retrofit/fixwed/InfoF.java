package com.example.x.bonus.retrofit.fixwed;

import com.example.x.bonus.retrofit.Userinfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mobi on 24.06.2017.
 */

public class InfoF {
    @SerializedName("userinfo")
    @Expose
    private Userinfo userinfo;
    @SerializedName("mycompany")
    @Expose
    private Mycompany mycompany;

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public Mycompany getMycompany() {
        return mycompany;
    }

    public void setMycompany(Mycompany mycompany) {
        this.mycompany = mycompany;
    }


}
