package com.peter.demo.activity.word;

import android.support.v4.app.Fragment;

import com.peter.demo.activity.template.FastHeaderActivity;

/**
 * Created by songzhongkun on 15/11/3 19:05.
 */
public class WordListActivity extends FastHeaderActivity {

    @Override
    public Fragment getFragment() {
        return new WordListFragment();
    }
}
