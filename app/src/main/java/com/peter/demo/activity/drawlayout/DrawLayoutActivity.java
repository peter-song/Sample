package com.peter.demo.activity.drawlayout;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;


public class DrawLayoutActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new DrawLayoutFragment();
    }
}
