package com.example.x.bonus.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by x on 16.06.2017.
 */

public class CategoryId {

    @SerializedName("company_category")
    @Expose
    private String companyCategory;
    @SerializedName("icon")
    @Expose
    private String icon;

    public String getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}