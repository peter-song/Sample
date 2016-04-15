package com.peter.demo.activity.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.peter.demo.R;
import com.peter.demo.net.Apis;
import com.peter.demo.net.HttpCallbackListener;
import com.peter.demo.net.HttpUtil;
import com.peter.demo.net.Utility;
import com.peter.demo.service.AutoUpdateService;
import com.peter.demo.base.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 显示天气类
 *
 * @author peter
 * @ClassName: WeatherFragment
 * @date 2014-12-23 下午6:20:30
 */
public class WeatherFragment extends BaseFragment implements OnClickListener {

    private TextView cityNameText;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;
    private Button switchCity;
    private Button refreshWeather;
    String countyCode = "";

    @Override
    public int layoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    public void initUI() {
        // 初始化各个控件
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);

        // 为控件设置监听事件
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            countyCode = bundle.getString("county_code");
        }
        if (!TextUtils.isEmpty(countyCode)) {
            publishText.setText(R.string.synchronousing);
            queryWeatherCode(countyCode);
        } else {
            // 显示天气
            showWeather();
        }
        // 显示后启动自动更新服务(仅更新,但不显示到界面上,需要手动点击更新按钮才能更新到界面上)
        mContext.startService(new Intent(mContext, AutoUpdateService.class));
        // 定时更新天气信息到界面上(代替页面上手动点击更新按钮)
        timerTask();
    }

    @Override
    public void initHeader() {
        getHeader().hide();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city:// 选择城市
                ChooseAreaFragment chooseAreaFragment = new ChooseAreaFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("from_weather_activity", true);
                chooseAreaFragment.setArguments(bundle);
                openNewFragment(chooseAreaFragment, true);
                break;
            case R.id.refresh_weather:// 更新天气
                publishText.setText(R.string.synchronousing);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                String weatherCode = prefs.getString("weather_code", "");
                if (!TextUtils.isEmpty(weatherCode)) {
                    queryWeatherInfo(weatherCode);
                }
                break;
        }
    }

    /**
     * 查询县级代号所对应的天气代号
     */
    private void queryWeatherCode(String countyCode) {
        String address = Apis.CITY_ADDRESS_PREF + countyCode + ".xml";
        queryFromServer(address, "countyCode");
    }

    /**
     * 查询天气代号所对应的天气
     */
    private void queryWeatherInfo(String weatherCode) {
        String address = Apis.WEATHER_ADDRESS_PREF + weatherCode + ".html";
        queryFromServer(address, "weatherCode");
    }

    /**
     * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
     */
    private void queryFromServer(final String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        // 从服务器返回的数据中解析出天气代号
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    // 处理服务器返回的天气信息
                    Utility.handleWeatherResponse(mContext, response);
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 显示天气信息
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText(R.string.synchronousFail);
                    }
                });
            }
        });
    }

    /**
     * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上。
     */
    public void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        cityNameText.setText(prefs.getString("city_name", ""));
        temp1Text.setText(prefs.getString("temp1", ""));
        temp2Text.setText(prefs.getString("temp2", ""));
        weatherDespText.setText(prefs.getString("weather_desp", ""));
        publishText.setText(mContext.getResources().getString(R.string.today) + prefs.getString("publish_time", "") + mContext.getResources().getString(R.string.send));
        currentDateText.setText(prefs.getString("current_date", ""));
    }

    /**
     * 定时任务
     *
     * @return void 返回类型
     * @throws
     * @Title: timerTask
     */
    public void timerTask() {
        // 创建定时线程执行更新任务
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        }, 1000 * 5, 1000 * 60 * 60);// 定时任务1分钟执行一次
    }

    /**
     * 消息处理器的应用
     */
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    showWeather();
                    break;
            }
        }
    };

    @Override
    public boolean onFragmentBackPressed() {
        return super.onFragmentBackPressed();
    }
}
