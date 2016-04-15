package com.peter.demo.activity.designpatterns;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * 设计模式
 * Created by songzhongkun on 16/3/30 10:19.
 */
public class MainActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new MainFragment();
    }
}
