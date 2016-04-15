package com.peter.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.peter.demo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by songzhongkun on 15/11/11 17:54.
 */
public class DrawableActivity extends Activity {

    @Bind(R.id.ll_down)
    LinearLayout ll_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.shape1, R.id.shape2, R.id.shape3})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.shape1:
                ll_down.setBackgroundResource(R.drawable.shape1);
                break;
            case R.id.shape2:
                ll_down.setBackgroundResource(R.drawable.shape2);
                break;
            case R.id.shape3:
                ll_down.setBackgroundResource(R.drawable.shape3);
                break;
        }
    }
}
