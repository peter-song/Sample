package com.peter.demo.activity.card2d;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * Created by songzhongkun on 16/3/24 17:21.
 */
public class Card2dActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new Card2dFragment();
    }
}
