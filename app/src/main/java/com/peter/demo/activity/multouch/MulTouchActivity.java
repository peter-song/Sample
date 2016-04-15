package com.peter.demo.activity.multouch;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * Created by songzhongkun on 16/3/25 14:31.
 */
public class MulTouchActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new MylTouchFragment();
    }
}
