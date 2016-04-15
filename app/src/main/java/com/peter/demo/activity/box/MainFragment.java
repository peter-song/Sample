package com.peter.demo.activity.box;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.peter.common.adapter.HomePagerAdapter;
import com.peter.common.model.Advertising;
import com.peter.common.view.AutoScrollViewPager;
import com.peter.demo.R;
import com.peter.demo.config.Constants;
import com.peter.demo.utils.HeaderHelper;
import com.peter.demo.base.BaseFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songzhongkun on 15/10/20 11:46.
 */
public class MainFragment extends BaseFragment implements SurfaceHolder.Callback, View.OnClickListener {

    private RadioButton rbPickup, rbDelivery, rbDeposit, rbRecharge, rbInquiry, rbHelp;
    private MediaPlayer player;
    private SurfaceView svAdvertisement;
    private LinearLayout lvHomeBannerPointer;
    private SurfaceHolder surfaceHolder;
    private ImageView ivAnquan, ivXunsu, ivBianjie;
    private AutoScrollViewPager viewPager;
    private String videoPath;

    private int previousPointEnale = 0;// 广告动画当前页位置点

    @Override
    public int layoutId() {
        return R.layout.fragment_box_main;
    }

    @Override
    public void initUI() {
        // 获取控件对象
        rbPickup = (RadioButton) findViewById(R.id.rb_pickup);
        rbDelivery = (RadioButton) findViewById(R.id.rb_delivery);
        rbDeposit = (RadioButton) findViewById(R.id.rb_deposit);
        rbRecharge = (RadioButton) findViewById(R.id.rb_recharge);
        rbInquiry = (RadioButton) findViewById(R.id.rb_inquiry);
        rbHelp = (RadioButton) findViewById(R.id.rb_help);
        svAdvertisement = (SurfaceView) findViewById(R.id.sv_advertisement);
        viewPager = (AutoScrollViewPager) findViewById(R.id.vp_advertisement);
        lvHomeBannerPointer = (LinearLayout) findViewById(R.id.home_banner_pointer);
        ivAnquan = (ImageView) findViewById(R.id.iv_anquan);
        ivXunsu = (ImageView) findViewById(R.id.iv_xunsu);
        ivBianjie = (ImageView) findViewById(R.id.iv_bianjie);

        // 为对象设置监听事件
        rbPickup.setOnClickListener(this);
        rbDelivery.setOnClickListener(this);
        rbDeposit.setOnClickListener(this);
        rbRecharge.setOnClickListener(this);
        rbInquiry.setOnClickListener(this);
        rbHelp.setOnClickListener(this);
        ivAnquan.setOnClickListener(this);
        ivXunsu.setOnClickListener(this);
        ivBianjie.setOnClickListener(this);
    }


    @Override
    public void initData() {
        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        TextView tvTime = (TextView) findViewById(R.id.tv_time);
        // 顶部显示当前日期
        HeaderHelper.updateCurrentDateTime(mContext, tvDate, tvTime);
        // 加载广告
        showAdvertisement(1);
    }

    @Override
    public void initHeader() {
        getHeader().hide();
    }

    /**
     * 获取广告类型
     *
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     * @Title: getAdvertisementType
     */
    public void showAdvertisement(int type) {
        // 服务器获取的广告
        List<Advertising> advertisings = new ArrayList<Advertising>();
        if (advertisings != null && advertisings.size() > 0) {
            type = advertisings.get(0).getType();
            videoPath = advertisings.get(0).getVideo_path();
        }
        if (type == Constants.AdvertisingType.IMG) {
            viewPager.setVisibility(View.VISIBLE);
            svAdvertisement.setVisibility(View.GONE);
            loadBannersData(advertisings);
        } else if (type == Constants.AdvertisingType.VIDEO) {
            viewPager.setVisibility(View.GONE);
            svAdvertisement.setVisibility(View.VISIBLE);
            surfaceHolder = svAdvertisement.getHolder(); // SurfaceHolder是SurfaceView的控制接口
            surfaceHolder.addCallback(this); // 因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
            // 4.0版本之下需要设置的属性
            // 设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到界面
            // surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    /**
     * 获取默认Banner图片
     */
    public List<Advertising> getDefaultBanners() {
        List<Advertising> list = new ArrayList<Advertising>();
        Advertising banner1 = new Advertising();
        banner1.setIcon(R.mipmap.home_banner_1);
        list.add(banner1);
        Advertising banner2 = new Advertising();
        banner2.setIcon(R.mipmap.home_banner_2);
        list.add(banner2);
        Advertising banner3 = new Advertising();
        banner3.setIcon(R.mipmap.home_banner_3);
        list.add(banner3);
        return list;
    }

    /**
     * 加载banner图
     *
     * @param @param banners 设定文件
     * @return void 返回类型
     * @throws
     * @Title: loadBannersData
     */
    private void loadBannersData(List<Advertising> banners) {

        // 取到默认Banner图
        List<Advertising> newBanners = getDefaultBanners();

        // 如果banners为空,从服务器获取Banner图
        if (banners == null || banners.size() == 0) {
            for (Advertising banner : banners) {
                newBanners.get(banner.getPosition() - 1).setImg_path(banner.getImg_path());
            }
        }

        // 如果没有图片或只有一张图片,不显示广告正下方的小圆点,否则显示并初始化小圆点
        if (newBanners == null || newBanners.size() <= 1) {
            lvHomeBannerPointer.setVisibility(View.GONE);
        } else {
            lvHomeBannerPointer.setVisibility(View.VISIBLE);
            lvHomeBannerPointer.removeAllViewsInLayout();

            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
            int marginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
            for (int i = 0; i < newBanners.size(); i++) {
                // 初始化广告条正下方的点
                View dot = new View(mContext);
                dot.setBackgroundResource(R.drawable.selector_point_background);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px, px);
                params.leftMargin = marginPx;
                dot.setLayoutParams(params);
                dot.setEnabled(false);
                lvHomeBannerPointer.addView(dot);
            }
        }

        HomePagerAdapter pagerAdapter = new HomePagerAdapter(mContext, newBanners);
        viewPager.setAdapter(pagerAdapter);
        final int count = newBanners.size();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // 获取新的位置
                int newPosition = arg0 % count;
                // 消除上一次的状态点
                lvHomeBannerPointer.getChildAt(previousPointEnale).setEnabled(false);
                // 设置当前的状态点“点”
                lvHomeBannerPointer.getChildAt(newPosition).setEnabled(true);
                // 记录位置
                previousPointEnale = newPosition;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        viewPager.stopAutoScroll();
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        viewPager.startAutoScroll();
                        break;
                    default:
                        break;
                }
            }
        });

        // 初始化广告条，当前索引Integer.MAX_VALUE的一半
        viewPager.setCycle(true);
        viewPager.setInterval(3000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(100); // 设置当前选中的Page，会触发onPageChangListener.onPageSelected方法
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_pickup:// 取件
                openNewFragment(new PickupFragment(), true);
                break;
            case R.id.rb_delivery:// 投递
                break;
            case R.id.rb_deposit:// 寄存
                break;
            case R.id.rb_recharge:// 充值
                break;
            case R.id.rb_inquiry:// 查询
                break;
            case R.id.rb_help:// 帮助
                openNewFragment(new HelpFragment(), true);
                break;
            case R.id.iv_anquan:
                showAdvertisement(Constants.AdvertisingType.IMG);
                break;
            case R.id.iv_xunsu:
                showAdvertisement(Constants.AdvertisingType.VIDEO);
                break;
            case R.id.iv_bianjie:
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
    }

    /**
     * 播放广告视频
     *
     * @return void 返回类型
     * @throws
     * @Title: play
     */
    public void play() {
        try {
            if (videoPath == null) {
                videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "movie.flv";
            }
            if (!new File(videoPath).exists()) {
                alert(R.string.fileNotExist);
                return;
            }
            player = new MediaPlayer();
            // 初始状态
            player.reset();
            // 设置音乐流的类型
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置video影片以SurfaceViewHolder播放
            player.setDisplay(surfaceHolder);
            // 设置资源
            player.setDataSource(videoPath);
            // 缓冲
            player.prepare();
            // 设置循环播放
            player.setLooping(true);
            // 播放
            player.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
