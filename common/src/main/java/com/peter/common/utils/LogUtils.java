package com.peter.common.utils;

import android.util.Log;

import com.peter.common.utils.log.DefaultLogger;
import com.peter.common.utils.log.ILog;
import com.peter.common.utils.log.LoggerDecorator;
import com.peter.common.utils.log.Slf4jLogger;

/**
 * 日志操作赋值类
 * Created by songzhongkun on 15/10/16.
 */
public class LogUtils {
    private ILog logger;

    private LogUtils() {
        logger = new DefaultLogger();
    }

    private static class LazyHolder {
        private static final LogUtils INSTANCE = new LogUtils();
    }

    public static ILog getLogger(Class cls) {
        ILog logger;
        //logger= new DefaultLogger();
        logger = new Slf4jLogger(cls);
        return new LoggerDecorator(logger);
    }

    public static ILog getLogger(String tag) {
        ILog logger;
        //logger= new DefaultLogger();
        logger = new Slf4jLogger(tag);
        return new LoggerDecorator(logger);
    }

    /**
     * 配置 日志框架，请在 application start 时执行
     *
     * @param para 应用名，用于区分与日志对应的应用;文件前缀；日志级别等
     */
    public static void configureLogger(ConfigPara para) {
        if (para == null) {
            para = new ConfigPara();
            para.enable = true;
            para.level = Log.VERBOSE;
            para.outputFilePrefix = "log";
            para.outputDir = "log";
        }
        //配置日志框架
        Slf4jLogger.configureLogbackDirectly(para.outputDir, para.outputFilePrefix);
        LoggerDecorator.setLevel(para.level);
        LoggerDecorator.setEnable(para.enable);
    }

    public static class ConfigPara {
        public String outputFilePrefix;
        public String outputDir;
        public int level;
        public boolean enable;
    }
}
