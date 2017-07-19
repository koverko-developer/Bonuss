package com.example.x.bonus.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by x on 22.06.2017.
 */

public class Info {

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
