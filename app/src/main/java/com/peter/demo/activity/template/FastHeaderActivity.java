package com.peter.demo.activity.template;

import com.peter.demo.R;
import com.peter.demo.base.BaseActivity;

/**
 * Created by songzhongkun on 15/11/2 17:46.
 */
public abstract class FastHeaderActivity extends BaseActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_base;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getFragmentHolderId() {
        return R.id.fl_viewHolder;
    }

    @Override
    public int getHeaderViewId() {
        return R.id.hv_header;
    }

}
