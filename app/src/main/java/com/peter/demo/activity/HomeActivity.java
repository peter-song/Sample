package com.peter.demo.activity;

import android.support.v4.app.Fragment;

import com.peter.demo.R;
import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * 主类
 * Created by songzhongkun on 15/10/19 23:28.
 */
public class HomeActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new HomeFragment();
    }

    @Override
    public void onBackPressed() {
        long newTime = System.currentTimeMillis();
        if ((newTime - oldTime) > 2000) {
            alert(R.string.app_exit);
            oldTime = newTime;
        } else {
            super.onBackPressed();
        }
    }

}
