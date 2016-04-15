package com.peter.demo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.common.utils.log.ILog;
import com.peter.common.view.HeaderView;
import com.peter.demo.app.BaseApp;

import butterknife.ButterKnife;

/**
 * 基础fragment
 * Created by songzhongkun on 15/11/2 18:24.
 */
public abstract class BaseFragment extends Fragment {

    protected FragmentActivity mContext;
    protected View rootView;
    private BaseActivity baseActivity;
    public ILog logger;
    private BaseApp app;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            baseActivity = (BaseActivity) context;
            logger = baseActivity.logger;
            app = baseActivity.app;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(layoutId(), container, false);

            initUI();
            initData();
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    /**
     * 根据tag查找fragment
     */
    public Fragment findFragmentByTag(String tag) {
        return ((BaseActivity) getActivity()).findFragmentByTag(tag);
    }

    /**
     * 打开一个新的fragment
     *
     * @param fragment
     */
    public void openNewFragment(Fragment fragment) {
        openNewFragment(fragment, true);
    }

    /**
     * 打开一个新的fragment（重载）
     *
     * @param fragment
     * @param addToStack
     */
    public void openNewFragment(Fragment fragment, Boolean addToStack) {
        ((BaseActivity) getActivity()).openNewFragment(fragment, addToStack);
    }

    /**
     * 根据id查找对象
     *
     * @param id
     * @return
     */
    public View findViewById(int id) {
        return rootView.findViewById(id);
    }

    /**
     * 关闭最新的fragment
     */
    public void closeTopFragment() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            ((BaseActivity) activity).closeTopFragment();
        }
    }

    /**
     * 关闭全部fragment
     */
    public void closeAllFragment() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            ((BaseActivity) activity).closeAllFragment();
        }
    }

    /**
     * 获取activity对象
     *
     * @return
     */
    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    /**
     * 获取全局application
     *
     * @return
     */
    public BaseApp getApp() {
        return app;
    }

    /**
     * 获取头部视图
     *
     * @return
     */
    public HeaderView getHeader() {
        return getBaseActivity().headerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initHeader();
    }

    /**
     * fragment点击返回键
     *
     * @return
     */
    public boolean onFragmentBackPressed() {
        return false;
    }

    /**
     * toast提示
     *
     * @param msg 提示文字
     */
    public void alert(String msg) {
        ((BaseActivity) getActivity()).alert(msg);
    }

    /**
     * toast提示
     *
     * @param resourceId 提示资源
     */
    public void alert(int resourceId) {
        ((BaseActivity) getActivity()).alert(resourceId);
    }

    /**
     * 布局资源文件
     */
    public abstract int layoutId();

    /**
     * 初始化UI
     */
    public abstract void initUI();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化头部
     */
    public abstract void initHeader();

}
