package com.peter.demo.app;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Environment;
import android.util.Log;

import com.peter.common.utils.LogUtils;
import com.peter.common.utils.log.ILog;
import com.peter.demo.R;
import com.peter.demo.config.Constants;
import com.peter.demo.model.hotel.LoginInfo;

import java.util.HashMap;

/**
 * Created by songzhongkun on 15/10/15.
 */
public class BaseApp extends Application {

    private ILog logger;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundMap;

    private LoginInfo loginInfo;

    private static Context context;
    private static BaseApp app;
    public static BaseApp getApp(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        loginInfo  = new LoginInfo();

        configLogger();

        soundPool = new SoundPool(10, AudioManager.STREAM_ALARM, 5);
        soundMap = new HashMap<Integer, Integer>();
        // 初始化语音
        getSoundMap().put(Constants.Voice.LOGIN, getSoundPool().load(this, R.raw.login, 1)); // 请输入手机号码及短信验证码
        getSoundMap().put(Constants.Voice.INPUT_PASSWORD, getSoundPool().load(this, R.raw.qujianpwassowrd, 1));// 请输入取件密码
        context = getApplicationContext();
    }

    /**
     * 配置日志 组件
     */
    public void configLogger() {
        //配置 日志框架.此后,logger和日志 才起效
        LogUtils.ConfigPara configPara = new LogUtils.ConfigPara();
        configPara.enable = true;//开关
        configPara.level = Log.VERBOSE;//级别
        configPara.outputFilePrefix = "ui-log";//前缀
        configPara.outputDir = Environment.getExternalStorageDirectory() + "/weather/log/";//路径
        LogUtils.configureLogger(configPara);
        logger = LogUtils.getLogger(BaseApp.class);
        logger.debug("配置日志组件 完成");
    }

    public static Context getContext() {
        return context;
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    public void setSoundPool(SoundPool soundPool) {
        this.soundPool = soundPool;
    }

    public HashMap<Integer, Integer> getSoundMap() {
        return soundMap;
    }

    public void setSoundMap(HashMap<Integer, Integer> soundMap) {
        this.soundMap = soundMap;
    }

    public LoginInfo getLogin(){
        return loginInfo;
    }
}
