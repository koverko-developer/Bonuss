package com.example.x.bonus.fragments.classes;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.image.ImageType;

/**
 * Created by x on 14.06.2017.
 */

public class QRObject extends BaseObservable {

    private ObservableField<String> qr;
    private ObservableField<String> phone;
    private ObservableField<String> invait;
    private ObservableBoolean isQR;
    private ObservableField<String> shtrih;


    public QRObject(){

        qr = new ObservableField<>();
        phone = new ObservableField<>();
        invait = new ObservableField<>();
        isQR = new ObservableBoolean(false);
        shtrih = new ObservableField<>();

    }

    public ObservableField<String> getShtrih() {
        return shtrih;
    }

    public void setShtrih(ObservableField<String> pshtrih) {
        shtrih.set(pshtrih.get());
    }

    public ObservableBoolean getIsQR() {
        return isQR;
    }

    public void setIsQR(ObservableBoolean pisQR) {
        isQR.set(pisQR.get());
    }

    public ObservableField<String> getQr() {
        return qr;
    }

    public void setQr(ObservableField<String> pqr) {
        qr.set(pqr.get());
    }

    public ObservableField<String> getPhone() {
        return phone;
    }

    public void setPhone(ObservableField<String> pphone) {
        phone.set(pphone.get());
    }

    public ObservableField<String> getInvait() {
        return invait;
    }

    public void setInvait(ObservableField<String> pinvait) {
        invait.set(pinvait.get());
    }

    @BindingAdapter({"bind:srcqr"})
    public static void setImageUrl(ImageView imageView, String url) {
//        Picasso.with(imageView.getContext())
//                .load(url)
//                .into(imageView);

        Bitmap myBitmap = QRCode.from(url).to(ImageType.PNG).withSize(150,150).bitmap();
        imageView.setImageBitmap(myBitmap);
    }

//    @BindingAdapter({"android:src"})
//    public static void setImageBitmap(ImageView imageView, String data) {
//        try {
//            byte[] chartData = data.getBytes(Charset.forName("UTF-8"));
//            Bitmap bm = BitmapFactory.decodeByteArray(chartData, 0, chartData.length);
//            imageView.setImageBitmap(bm);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName){
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fontName));
    }


}
