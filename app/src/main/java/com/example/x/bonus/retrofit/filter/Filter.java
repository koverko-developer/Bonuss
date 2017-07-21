package com.example.x.bonus.retrofit.filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mobi app on 21.07.2017.
 */

public class Filter {

    @SerializedName("category")
    @Expose
    private List<CategoryFilter> category = null;
    @SerializedName("city")
    @Expose
    private List<CityFilter> city = null;

    public List<CategoryFilter> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryFilter> category) {
        this.category = category;
    }

    public List<CityFilter> getCity() {
        return city;
    }

    public void setCity(List<CityFilter> city) {
        this.city = city;
    }

}
