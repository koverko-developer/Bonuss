package com.example.x.bonus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.x.bonus.R;

/**
 * Created by mobi on 24.06.2017.
 */

public class StepsView extends LinearLayout {


    /*


     */

    //Header text buy count

    TextView tvBuy1,tvBuy2,tvBuy3,tvBuy4;

    //Circle text steps procent
    TextView tvStep1,tvStep2,tvStep3,tvStep4;

    //Progress steps
    ProgressBar progress1,progress2,progress3;

    public StepsView(Context context) {
        super(context);
        initializeViews(context);
    }

    public StepsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public StepsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_steps_sale, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tvBuy1 = (TextView) findViewById(R.id.textStep1);
        tvBuy2 = (TextView) findViewById(R.id.textStep2);
        tvBuy3 = (TextView) findViewById(R.id.textStep3);
        tvBuy4 = (TextView) findViewById(R.id.textStep4);

        //Circle text steps procent
        tvStep1 = (TextView) findViewById(R.id.textViewStep1);
        tvStep2 = (TextView) findViewById(R.id.textViewStep2);
        tvStep3 = (TextView) findViewById(R.id.textViewStep3);
        tvStep4 = (TextView) findViewById(R.id.textViewStep4);

        //Progress steps
        progress1 = (ProgressBar) findViewById(R.id.progressStep1);
        progress2 = (ProgressBar) findViewById(R.id.progressStep2);
        progress3 = (ProgressBar) findViewById(R.id.progressStep3);

    }

}
