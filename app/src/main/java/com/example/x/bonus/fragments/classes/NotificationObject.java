package com.example.x.bonus.fragments.classes;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

/**
 * Created by mobi app on 06.07.2017.
 */

public class NotificationObject extends BaseObservable {

    private ObservableField<String> imgOrganization;
    private ObservableField<String> nameOrganization;
    private ObservableField<String> city;
    private ObservableField<String> imgAction;
    private ObservableField<String> titleAction;
    private ObservableField<String> timeAction;
    private ObservableField<String> textAction;
    private ObservableField<String> id_news;
    private ObservableField<String> id_company;

    public NotificationObject(){
        imgOrganization = new ObservableField<>();
        nameOrganization = new ObservableField<>();
        city = new ObservableField<>();
        imgAction = new ObservableField<>();
        titleAction = new ObservableField<>();
        timeAction = new ObservableField<>();
        textAction = new ObservableField<>();
        id_news = new ObservableField<>();
        id_company = new ObservableField<>();
    }

    public ObservableField<String> getImgOrganization() {
        return imgOrganization;
    }

    public void setImgOrganization(ObservableField<String> pimgOrganization) {
        imgOrganization.set(pimgOrganization.get());
    }

    public ObservableField<String> getNameOrganization() {
        return nameOrganization;
    }

    public void setNameOrganization(ObservableField<String> pnameOrganization) {
        nameOrganization.set(pnameOrganization.get());
    }

    public ObservableField<String> getCity() {
        return city;
    }

    public void setCity(ObservableField<String> pcity) {
        city.set(pcity.get());
    }

    public ObservableField<String> getImgAction() {
        return imgAction;
    }

    public void setImgAction(ObservableField<String> pimgAction) {
        imgAction.set(pimgAction.get());
    }

    public ObservableField<String> getTitleAction() {
        return titleAction;
    }

    public void setTitleAction(ObservableField<String> ptitleAction) {
        titleAction.set(ptitleAction.get());
    }

    public ObservableField<String> getTimeAction() {
        return timeAction;
    }

    public void setTimeAction(ObservableField<String> ptimeAction) {
        timeAction.set(ptimeAction.get());
    }

    public ObservableField<String> getTextAction() {
        return textAction;
    }

    public void setTextAction(ObservableField<String> ptextAction) {
        textAction.set(ptextAction.get());
    }

    public ObservableField<String> getId_news() {
        return id_news;
    }

    public void setId_news(ObservableField<String> pid_news) {
        id_news.set(pid_news.get());
    }

    public ObservableField<String> getId_company() {
        return id_company;
    }

    public void setId_company(ObservableField<String> id_company) {
        this.id_company = id_company;
    }
}
