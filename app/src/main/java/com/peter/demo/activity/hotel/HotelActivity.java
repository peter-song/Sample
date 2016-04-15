package com.peter.demo.activity.hotel;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * Created by songzhongkun on 15/11/5 11:08.
 */
public class HotelActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new HotelFragment();
    }
}
