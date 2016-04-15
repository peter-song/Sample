package com.peter.demo.activity.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

import java.util.ArrayList;

/**
 *
 * Created by songzhongkun on 15/12/3 10:34.
 */
public class ViewPagerFragment extends BaseFragment {

    ViewPager pager = null;
    PagerTabStrip tabStrip = null;
    ArrayList<View> viewContainter = new ArrayList<View>();
    ArrayList<String> titleContainer = new ArrayList<String>();

    @Override
    public int layoutId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void initUI() {
        pager = (ViewPager) this.findViewById(R.id.viewpager);
        tabStrip = (PagerTabStrip) this.findViewById(R.id.tabstrip);
        //取消tab下面的长横线
        tabStrip.setDrawFullUnderline(false);
        //设置tab的背景色
        tabStrip.setBackgroundColor(this.getResources().getColor(R.color.white));
        //设置当前tab页签的下划线颜色
        tabStrip.setTabIndicatorColor(this.getResources().getColor(R.color.red));
        tabStrip.setTextSpacing(100);

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.tab1, null);
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.tab2, null);
        View view3 = LayoutInflater.from(mContext).inflate(R.layout.tab3, null);
        View view4 = LayoutInflater.from(mContext).inflate(R.layout.tab4, null);
        //viewpager开始添加view
        viewContainter.add(view1);
        viewContainter.add(view2);
        viewContainter.add(view3);
        viewContainter.add(view4);
    }

    @Override
    public void initData() {
        //页签项
        titleContainer.add("网易新闻");
        titleContainer.add("网易体育");
        titleContainer.add("网易财经");
        titleContainer.add("网易女人");

        pager.setAdapter(new PagerAdapter() {

            //viewpager中的组件数量
            @Override
            public int getCount() {
                return viewContainter.size();
            }

            //滑动切换的时候销毁当前的组件
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                (container).removeView(viewContainter.get(position));
            }

            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                (container).addView(viewContainter.get(position));
                return viewContainter.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleContainer.get(position);
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
                logger.debug("--------changed:" + arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                logger.debug("-------scrolled arg0:" + arg0);
                logger.debug("-------scrolled arg1:" + arg1);
                logger.debug("-------scrolled arg2:" + arg2);
            }

            @Override
            public void onPageSelected(int arg0) {
                logger.debug("------selected:" + arg0);
            }
        });
    }

    @Override
    public void initHeader() {
        getHeader().setTitle("ViewPager");
    }

}
