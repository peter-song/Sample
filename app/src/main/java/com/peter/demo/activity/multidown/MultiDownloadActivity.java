package com.peter.demo.activity.multidown;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * 多线程断点续传下载
 * Created by songzhongkun on 16/3/29 10:17.
 */
public class MultiDownloadActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new MultiDownloadFragment();
    }
}
