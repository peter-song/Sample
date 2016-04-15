package com.peter.demo.activity.drawlayout;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * DrawLayout侧滑菜单
 * Created by songzhongkun on 15/12/3 09:36.
 */
public class DrawLayoutFragment extends BaseFragment implements DrawerLayout.DrawerListener {
    // 抽屉菜单对象
    public DrawerLayout drawerLayout;
    @Bind(R.id.ll_left_layout)
    LinearLayout leftLayout;
    @Bind(R.id.ll_right_layout)
    LinearLayout rightLayout;
    @Bind(R.id.ll_right_menu)
    LinearLayout ll_right_menu;
    @Bind(R.id.ll_right_menu2)
    LinearLayout ll_right_menu2;

    @Override
    public int layoutId() {
        return R.layout.fragemnt_drawlayout;
    }

    @Override
    public void initUI() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawerLayout.setDrawerListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initHeader() {

    }

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.iv_right2})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                drawerLayout.openDrawer(leftLayout);
                break;
            case R.id.iv_right:
                ll_right_menu.setVisibility(View.VISIBLE);
                ll_right_menu2.setVisibility(View.GONE);
                drawerLayout.openDrawer(rightLayout);
                break;
            case R.id.iv_right2:
                ll_right_menu.setVisibility(View.GONE);
                ll_right_menu2.setVisibility(View.VISIBLE);
                drawerLayout.openDrawer(rightLayout);
                break;
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }
}
