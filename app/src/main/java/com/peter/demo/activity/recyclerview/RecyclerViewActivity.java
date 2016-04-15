package com.peter.demo.activity.recyclerview;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * Created by songzhongkun on 15/11/9 12:14.
 */
public class RecyclerViewActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new RecyclerViewFragment();
    }

}
