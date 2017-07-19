package com.example.x.bonus.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by x on 22.06.2017.
 */

public class Userinfo {

    @SerializedName("col_buy")
    @Expose
    private String colBuy;
    @SerializedName("col_bal")
    @Expose
    private String colBal;

    @SerializedName("sum_buy")
    @Expose
    private String sum_buy;


    @SerializedName("status_company_user")
    @Expose
    private String statusCompanyUser;


    @SerializedName("last_buying")
    @Expose
    private String lastBuying;

    @SerializedName("price_last_buying")
    @Expose
    private String price_last_buying;

    @SerializedName("col_friend")
    @Expose
    private String col_friend;


    public String getColBuy() {
        return colBuy;
    }

    public void setColBuy(String colBuy) {
        this.colBuy = colBuy;
    }

    public String getColBal() {
        return colBal;
    }

    public void setColBal(String colBal) {
        this.colBal = colBal;
    }

    public String getStatusCompanyUser() {
        return statusCompanyUser;
    }

    public void setStatusCompanyUser(String statusCompanyUser) {
        this.statusCompanyUser = statusCompanyUser;
    }

    public String getSum_buy() {
        return sum_buy;
    }

    public void setSum_buy(String sum_buy) {
        this.sum_buy = sum_buy;
    }

    public String getLastBuying() {
        return lastBuying;
    }

    public void setLastBuying(String lastBuying) {
        this.lastBuying = lastBuying;
    }

    public String getPrice_last_buying() {
        return price_last_buying;
    }

    public void setPrice_last_buying(String price_last_buying) {
        this.price_last_buying = price_last_buying;
    }

    public String getCol_friend() {
        return col_friend;
    }

    public void setCol_friend(String col_friend) {
        this.col_friend = col_friend;
    }
}