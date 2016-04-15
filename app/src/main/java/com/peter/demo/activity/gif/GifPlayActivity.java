package com.peter.demo.activity.gif;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * gif动画播放
 * Created by songzhongkun on 15/10/25 14:22.
 */
public class GifPlayActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new GifPlayFragment();
    }

}
