package com.peter.demo.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 头部页面帮助类
 *
 * @author peter
 * @ClassName: HeaderHelper
 * @date 2015-1-9 下午3:31:45
 */
public class HeaderHelper {
    private static TextView _tvDate;// 年月日
    private static TextView _tvTime;// 时分

    /**
     * 更新当前时间
     *
     * @param @param context
     * @param @param dateId
     * @param @param timeId 设定文件
     * @return void 返回类型
     * @throws
     * @Title: updateCurrentDateTime
     */
    public static void updateCurrentDateTime(Activity context, TextView tvDate, TextView tvTime) {
        _tvDate = tvDate;
        _tvTime = tvTime;
        timerTask();
    }

    /**
     * 定时任务
     *
     * @return void 返回类型
     * @throws
     * @Title: timerTask
     */
    public static void timerTask() {
        // 创建定时线程执行更新任务
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);// 向Handler发送消息
            }
        }, 0, 1000 * 60);// 定时任务1分钟执行一次
    }

    /**
     * 消息处理器的应用
     */
    public static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String[] currentArr = getCurrentTimeArr();
                    _tvDate.setText(currentArr[0]);
                    _tvTime.setText(currentArr[1]);
                    break;
            }
        }
    };

    /**
     * 获取当前日期数组
     *
     * @param @return 设定文件
     * @return String[] 返回类型
     * @throws
     * @Title: getCurrentTimeArr
     */
    public static String[] getCurrentTimeArr() {
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat f = new SimpleDateFormat("yy/MM/dd hh:mm");
        String currentTime = f.format(c.getTime());
        String[] currentArr = currentTime.split(" ");
        if (h < 12) {
            currentArr[1] = "AM " + currentArr[1];
        } else {
            currentArr[1] = "PM " + currentArr[1];
        }
        return currentArr;
    }
}
