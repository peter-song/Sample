package com.peter.demo.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.peter.common.utils.LogUtils;
import com.peter.common.utils.log.ILog;
import com.peter.common.view.HeaderView;
import com.peter.demo.app.ActivityCollector;
import com.peter.demo.app.BaseApp;

import butterknife.ButterKnife;

/**
 * 基础类,继承Activity
 * Created by songzhongkun on 15/10/15.
 */
public abstract class BaseActivity extends FragmentActivity {

    public final String TAG = this.getClass().getSimpleName();
    public final ILog logger = LogUtils.getLogger(TAG);
    public BaseApp app;
    public Activity mContext;
    protected long oldTime;
    protected HeaderView headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置显示页面
        setContentView(layoutId());

        mContext = this;
        app = (BaseApp) this.getApplication();

        // 将正在创建的活动添加到活动管理器中
        ActivityCollector.addActivity(this);

        // 设置竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // 首次进入不显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        headerView = (HeaderView) findViewById(getHeaderViewId());

        //初始化UI和Data
        initUI();
        initData();

        ButterKnife.bind(mContext);

        if (getFragmentHolderId() != 0 && getFragment() != null) {
            openNewFragment(getFragment(), true);
        }
    }

    //布局资源文件
    public abstract int layoutId();

    //初始化UI
    public abstract void initUI();

    //初始化数据
    public abstract void initData();

    //fragment显示位置
    public abstract int getFragmentHolderId();

    //头部布局id
    public abstract int getHeaderViewId();

    //显示的fragment
    public abstract Fragment getFragment();

    /**
     * 打开新的fragment
     *
     * @param fragment
     * @param addToStack
     */
    public void openNewFragment(Fragment fragment, Boolean addToStack) {
        String tag = fragment.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(getFragmentHolderId(), fragment, tag);
        if (addToStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 关闭新打开的fragment
     */
    public void closeTopFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    /**
     * 关闭全部fragment
     */
    public void closeAllFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backEntryCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backEntryCount; i++) {
            closeTopFragment();
        }
    }

    /**
     * 点击返回键（如果存在fragment，关闭最上层的fragment，反正正常执行activity的返回操作）
     */
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backEntryCount = fragmentManager.getBackStackEntryCount();
        if (backEntryCount == 0) {
            super.onBackPressed();
            return;
        } else if (backEntryCount == 1) {
            finish();
            return;
        }

        FragmentManager.BackStackEntry lastEntry = fragmentManager.getBackStackEntryAt(backEntryCount - 1);
        BaseFragment fragment = (BaseFragment) fragmentManager.findFragmentByTag(lastEntry.getName());
        if (!fragment.onFragmentBackPressed()) {
            super.onBackPressed();
        }
    }

    /**
     * toast提示
     *
     * @param msg 提示文字
     */
    public void alert(String msg) {
        new com.peter.common.utils.ToastUtils(mContext).toast(msg);
    }

    /**
     * toast提示
     *
     * @param resourceId 提示资源
     */
    public void alert(int resourceId) {
        new com.peter.common.utils.ToastUtils(mContext).toast(resourceId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
