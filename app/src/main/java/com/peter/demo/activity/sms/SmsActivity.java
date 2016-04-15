package com.peter.demo.activity.sms;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * Created by songzhongkun on 16/2/24 16:56.
 */
public class SmsActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new SmsFragment();
    }
}
