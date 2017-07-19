package com.example.x.bonus.action;

/**
 * Created by mobi app on 26.06.2017.
 */

public class Shop {
    String name;
    String img;
    String href;

    public Shop(String name, String img, String href) {
        this.name = name;
        this.img = img;
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getHref() {
        return href;
    }

}
