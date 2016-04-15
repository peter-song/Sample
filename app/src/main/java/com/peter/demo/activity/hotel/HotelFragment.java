package com.peter.demo.activity.hotel;

import android.view.View;

import com.peter.common.net.Constants;
import com.peter.common.utils.SharedPreferenceUtils;
import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by songzhongkun on 15/11/5 11:10.
 */
public class HotelFragment extends BaseFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_hotel_main;
    }

    @Override
    public void initUI() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void initHeader() {
        getHeader().setTitle("Hotel");
        getHeader().hideLeftAndRight();
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isLogin = (boolean) SharedPreferenceUtils.get(mContext, Constants.IS_LOGIN, false);
        if (!isLogin) {
            openNewFragment(new LoginFragment(), true);
        }
    }

    @OnClick({R.id.exit})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.exit:
                SharedPreferenceUtils.clear(mContext);
                alert("退出成功");
                getBaseActivity().onBackPressed();
                break;
        }
    }

}
