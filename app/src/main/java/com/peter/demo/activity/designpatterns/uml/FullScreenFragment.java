package com.peter.demo.activity.designpatterns.uml;

import android.view.View;

import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;

/**
 * 全屏显示uml图
 * Created by songzhongkun on 16/3/31 16:28.
 */
public class FullScreenFragment extends BackHeaderFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_full_screen;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTopFragment();
            }
        });
    }
}
