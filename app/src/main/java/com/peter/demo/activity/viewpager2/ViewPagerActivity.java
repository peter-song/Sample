package com.peter.demo.activity.viewpager2;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.peter.common.view.HeaderView;
import com.peter.demo.R;
import com.peter.demo.base.BaseActivity;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songzhongkun on 16/4/15 14:27.
 */
public class ViewPagerActivity extends BaseActivity {
    public static final String KEY_ORDER_TYPE = "KEY_ORDER_TYPE";

    public static final int VALUE_TYPE_ALL = 0x100;
    public static final int VALUE_TYPE_WAIT_FOR_PAY = 0x101;
    public static final int VALUE_TYPE_WAIT_FOR_SEND = 0x102;
    public static final int VALUE_TYPE_WAIT_FOR_RECEIVER = 0x103;
    public static final int VALUE_TYPE_QIGUAI_DE_TUIHUANHUO = 0x104;

    public int initTabType = VALUE_TYPE_ALL;

    private TabPageIndicator tpi_indicator;
    private UnderlinePageIndicator upi_underLine;
    private List<Fragment> allOrderModules;
    private ViewPager vp_collectList;
    private boolean firstRun = true;
    private Handler mHandler = new Handler();

    private HeaderView headerView;


    @Override
    public int layoutId() {
        return R.layout.fragment_viewpage2;
    }

    @Override
    public void initUI() {
        tpi_indicator = (TabPageIndicator) findViewById(R.id.tpi_indicator);
        upi_underLine = (UnderlinePageIndicator) findViewById(R.id.upi_underLine);
        headerView = (HeaderView) findViewById(R.id.hv_header);
    }

    @Override
    public void initData() {
        headerView.setTitle("ViewPage测试");

        Intent mIntent = getIntent();
        initTabType = null == mIntent ? VALUE_TYPE_ALL : mIntent.getIntExtra(KEY_ORDER_TYPE, VALUE_TYPE_ALL);

        allOrderModules = new ArrayList<>();
        allOrderModules.add(new ViewPagerFragment11());
        allOrderModules.add(new ViewPagerFragment22());

        vp_collectList = (ViewPager) findViewById(R.id.vp_collectList);
        tpi_indicator.setSelectedTabTextColor(R.color.txt_color_red);
        tpi_indicator.setDefaultTabTextColor(R.color.txt_color_black);

        upi_underLine.setFades(false);

        vp_collectList.setOffscreenPageLimit(5);
        vp_collectList.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int i) {

                if (null != allOrderModules && i < allOrderModules.size()) {
                    return allOrderModules.get(i);
                }

                throw new IllegalArgumentException("Error fragment count from getCount...");

            }

            @Override
            public int getCount() {
                return allOrderModules.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {

                if (0 == position) {
                    return "First";
                } else if (1 == position) {
                    return "Second";
                } else {
                    return "unknown";
                }

            }
        });

        tpi_indicator.setViewPager(vp_collectList);
        upi_underLine.setViewPager(vp_collectList);
        upi_underLine.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                final int itemIndex = i;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tpi_indicator.setCurrentItem(itemIndex);
                    }
                }, 5);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        firstRun = true;
    }

    @Override
    public int getFragmentHolderId() {
        return 0;
    }

    @Override
    public int getHeaderViewId() {
        return 0;
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firstRun) {
            vp_collectList.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (initTabType == VALUE_TYPE_WAIT_FOR_PAY) {
                        vp_collectList.setCurrentItem(1, false);
                    } else if (initTabType == VALUE_TYPE_WAIT_FOR_SEND) {
                        vp_collectList.setCurrentItem(2, false);
                    } else if (initTabType == VALUE_TYPE_WAIT_FOR_RECEIVER) {
                        vp_collectList.setCurrentItem(3, false);
                    } else if (initTabType == VALUE_TYPE_QIGUAI_DE_TUIHUANHUO) {
                        vp_collectList.setCurrentItem(4, false);

                    }
                }
            }, 2);
            firstRun = false;
        }

    }
}
