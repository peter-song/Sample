package com.peter.demo.activity.template;

import android.view.View;

import com.peter.demo.base.BaseFragment;

/**
 * Created by songzhongkun on 15/11/4 09:47.
 */
public abstract class BackHeaderFragment extends BaseFragment {

    @Override
    public void initHeader() {
        getHeader().showLeftLayout();
        getHeader().showLeftImg();
        getHeader().setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTopFragment();
            }
        });
    }

}
