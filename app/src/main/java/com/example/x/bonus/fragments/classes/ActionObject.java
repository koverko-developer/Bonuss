package com.example.x.bonus.fragments.classes;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by x on 15.06.2017.
 */

public class ActionObject extends BaseObservable {

    private ObservableField<String> time;
    private ObservableField<String> name;
    private ObservableField<String> img;
    private ObservableField<String> text;

    public ActionObject() {
        time = new ObservableField<>();
        name = new ObservableField<>();
        img = new ObservableField<>();
        text = new ObservableField<>();
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public void setTime(ObservableField<String> ptime) {
        time.set("до "+ptime.get());
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> pname) {
        name.set(pname.get());
    }

    public ObservableField<String> getImg() {
        return img;
    }

    public void setImg(ObservableField<String> pimg) {
        img.set(pimg.get());
    }

    public ObservableField<String> getText() {
        return text;
    }

    public void setText(ObservableField<String> ptext) {
        text.set(ptext.get());
    }

    @BindingAdapter({"android:src"})
    public static void setImageUrl(ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

}
