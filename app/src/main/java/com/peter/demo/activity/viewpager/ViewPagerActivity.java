package com.peter.demo.activity.viewpager;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**

 * Created by songzhongkun on 15/12/3 10:55.
 */
public class ViewPagerActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new ViewPagerFragment();
    }
}
