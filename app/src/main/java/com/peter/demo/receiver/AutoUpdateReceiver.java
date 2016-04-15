package com.peter.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.peter.demo.service.AutoUpdateService;

/**
 * 自动更新天气广播类
 *
 * @author peter
 * @ClassName: AutoUpdateReceiver
 * @date 2014-12-23 下午6:22:23
 */
public class AutoUpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }

}
