package com.peter.demo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.peter.common.adapter.CommonAdapter;
import com.peter.demo.R;
import com.peter.demo.activity.box.MainActivity;
import com.peter.demo.activity.card2d.Card2dActivity;
import com.peter.demo.activity.custommenu.CustomMenuActivity;
import com.peter.demo.activity.drawlayout.DrawLayoutActivity;
import com.peter.demo.activity.gif.GifPlayActivity;
import com.peter.demo.activity.hotel.HotelActivity;
import com.peter.demo.activity.map.LocationModeSourceActivity;
import com.peter.demo.activity.metric.MetricActivity;
import com.peter.demo.activity.multidown.MultiDownloadActivity;
import com.peter.demo.activity.multouch.MulTouchActivity;
import com.peter.demo.activity.recyclerview.RecyclerViewActivity;
import com.peter.demo.activity.scrollerview.ScrollerViewActivity;
import com.peter.demo.activity.sms.SmsActivity;
import com.peter.demo.activity.viewpager.ViewPagerActivity;
import com.peter.demo.activity.weather.ChooseAreaActivity;
import com.peter.demo.activity.word.WordListActivity;
import com.peter.demo.model.HomeInfo;
import com.peter.demo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songzhongkun on 15/11/2 18:21.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private String[] names = {
            "悠闲天气",
            "近邻宝",
            "单词",
            "gif动画",
            "分辨率查看",
            "短信转发",
            "Hotel",
            "多线程断点续传下载",
            "RecyclerView",
            "DrawLayoutActivity",
            "ViewPager",
            "ViewPager2",
            "ScrollerView",
            "高德地图",
            "2d翻转",
            "自定义左右菜单",
            "多点触摸交互处理",
            "设计模式"
    };
    private int[] imgIds = {
            R.mipmap.weather_logo,
            R.mipmap.box_logo,
            R.mipmap.word_logo,
            R.mipmap.gif_logo,
            R.mipmap.metric_logo,
            R.mipmap.sms_logo,
            R.mipmap.hotel_logo,
            R.mipmap.thread_logo,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };
    private Class[] clazzs = {
            ChooseAreaActivity.class,
            MainActivity.class,
            WordListActivity.class,
            GifPlayActivity.class,
            MetricActivity.class,
            SmsActivity.class,
            HotelActivity.class,
            MultiDownloadActivity.class,
            RecyclerViewActivity.class,
            DrawLayoutActivity.class,
            ViewPagerActivity.class,
            com.peter.demo.activity.viewpager2.ViewPagerActivity.class,
            ScrollerViewActivity.class,
            LocationModeSourceActivity.class,
            Card2dActivity.class,
            CustomMenuActivity.class,
            MulTouchActivity.class,
            com.peter.demo.activity.designpatterns.MainActivity.class
    };

    @Override
    public int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUI() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        gridView.setAdapter(new CommonAdapter<HomeInfo>(mContext, initHomeList(), R.layout.item_grid) {
            @Override
            public void convert(ViewHolder helper, HomeInfo item) {
                helper.setText(R.id.tv_name, item.name);
                helper.setImageResource(R.id.iv_img, item.imgId);
            }
        });
    }

    @Override
    public void initHeader() {
        getHeader().hide();
    }

    /**
     * 初始化主页列表
     *
     * @return
     */
    private List<HomeInfo> initHomeList() {
        List<HomeInfo> homeList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            HomeInfo homeinfo = new HomeInfo();
            homeinfo.id = i + 1;
            homeinfo.name = names[i];
            homeinfo.imgId = imgIds[i];
            homeList.add(homeinfo);
        }

        return homeList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(mContext, clazzs[position]));
    }
}
