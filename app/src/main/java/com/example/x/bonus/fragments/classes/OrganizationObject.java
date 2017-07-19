package com.example.x.bonus.fragments.classes;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import com.example.x.bonus.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

/**
 * Created by x on 14.06.2017.
 */

public class OrganizationObject extends BaseObservable {
    private ObservableField<String> id;
    private ObservableField<String> name;
    private ObservableField<String> sale;
    private ObservableField<String> time;
    private ObservableField<String> img;
    private ObservableField<String> phone;
    private ObservableField<String> imgCategory;
    private ObservableField<String> nameCategory;
    private ObservableField<String> city;
    private ObservableBoolean isMy;
    private ObservableField<String> bt;

    private ObservableField<String> textCategoty;

    public OrganizationObject(){

        name = new ObservableField<>();
        sale = new ObservableField<>();
        time = new ObservableField<>();
        img = new ObservableField<>();
        phone = new ObservableField<>();
        imgCategory = new ObservableField<>();
        nameCategory = new ObservableField<>();
        isMy = new ObservableBoolean();
        textCategoty = new ObservableField<>();
        id = new ObservableField<>();
        city = new ObservableField<>();
        bt = new ObservableField<>();

    }

    public ObservableField<String> getBt() {
        return bt;
    }

    public void setBt(ObservableField<String> pbt) {
        bt.set(pbt.get());
    }

    public ObservableField<String> getCity() {
        return city;
    }

    public void setCity(ObservableField<String> pcity) {
        city.set(pcity.get());
    }


    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> pname) {
        name.set(pname.get());
    }

    public ObservableField<String> getSale() {
        return sale;
    }

    public void setSale(ObservableField<String> psale) {
        sale.set(psale.get()+"%");
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public void setTime(ObservableField<String> ptime) {
        time.set(ptime.get());
    }

    public ObservableField<String> getImg() {
        return img;
    }

    public void setImg(ObservableField<String> pimg) {
        img.set(pimg.get());
    }

    public ObservableField<String> getPhone() {
        return phone;
    }

    public void setPhone(ObservableField<String> pphone) {
        phone.set(pphone.get());
    }

    public ObservableField<String> getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(ObservableField<String> pimgCategory) {
        imgCategory.set(pimgCategory.get());
    }

    public ObservableField<String> getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(ObservableField<String> pnameCategory) {
        nameCategory.set(pnameCategory.get());
    }

    public ObservableBoolean getIsMy() {
        return isMy;
    }

    public void setIsMy(ObservableBoolean pisMy) {
        isMy.set(pisMy.get());
    }

    public ObservableField<String> getId() {
        return id;
    }

    public void setId(ObservableField<String> pid) {
        id.set(pid.get());
    }

    @BindingAdapter({"android:src"})
    public static void setImageUrl(ImageView imageView, String url) {
        try {
            if(url.contains("http:")) {
                Picasso.with(imageView.getContext())
                        .load(url)
                        .into(imageView);
            }else {
                byte[] decodedString = Base64.decode(url, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            }
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

}
