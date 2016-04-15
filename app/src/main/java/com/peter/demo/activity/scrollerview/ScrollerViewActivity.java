package com.peter.demo.activity.scrollerview;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * Created by songzhongkun on 15/12/3 16:22.
 */
public class ScrollerViewActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new ScrollerViewFragment();
    }
}
