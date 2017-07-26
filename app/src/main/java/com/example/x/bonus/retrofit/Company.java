package com.example.x.bonus.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by x on 16.06.2017.
 */

public class Company {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("unp")
    @Expose
    private String unp;
    @SerializedName("rekvizity")
    @Expose
    private String rekvizity;
    @SerializedName("diretor")
    @Expose
    private Object diretor;
    @SerializedName("osnovanie")
    @Expose
    private String osnovanie;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("loyalty_id")
    @Expose
    private String loyaltyId;
    @SerializedName("sale")
    @Expose
    private String sale;
    @SerializedName("loyalty_step")
    @Expose
    private LoyaltyStep loyaltyStep;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("category_id")
    @Expose
    private CategoryId categoryId;
    @SerializedName("invite_price")
    @Expose
    private String invitePrice;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("city")
    @Expose
    private City city;

    @SerializedName("info")
    @Expose
    private String info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnp() {
        return unp;
    }

    public void setUnp(String unp) {
        this.unp = unp;
    }

    public String getRekvizity() {
        return rekvizity;
    }

    public void setRekvizity(String rekvizity) {
        this.rekvizity = rekvizity;
    }

    public Object getDiretor() {
        return diretor;
    }

    public void setDiretor(Object diretor) {
        this.diretor = diretor;
    }

    public String getOsnovanie() {
        return osnovanie;
    }

    public void setOsnovanie(String osnovanie) {
        this.osnovanie = osnovanie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public LoyaltyStep getLoyaltyStep() {
        return loyaltyStep;
    }

    public void setLoyaltyStep(LoyaltyStep loyaltyStep) {
        this.loyaltyStep = loyaltyStep;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CategoryId getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryId categoryId) {
        this.categoryId = categoryId;
    }

    public String getInvitePrice() {
        return invitePrice;
    }

    public void setInvitePrice(String invitePrice) {
        this.invitePrice = invitePrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
