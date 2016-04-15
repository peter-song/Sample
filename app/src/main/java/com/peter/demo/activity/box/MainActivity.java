package com.peter.demo.activity.box;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * Created by songzhongkun on 15/10/20 11:46.
 */
public class MainActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new MainFragment();
    }
}
