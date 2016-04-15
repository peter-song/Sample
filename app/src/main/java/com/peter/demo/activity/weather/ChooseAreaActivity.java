package com.peter.demo.activity.weather;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * 选择地址类
 * Created by songzhongkun on 15/10/15.
 */
public class ChooseAreaActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new ChooseAreaFragment();
    }
}
