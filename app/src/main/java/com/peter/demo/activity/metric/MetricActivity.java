package com.peter.demo.activity.metric;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * 分辨率查看
 * Created by songzhongkun on 15/10/25 14:00.
 */
public class MetricActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new MetricFragment();
    }

}
