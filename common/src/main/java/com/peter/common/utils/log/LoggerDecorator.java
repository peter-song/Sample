package com.peter.common.utils.log;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 配置，日志组件.
 * 使用的 装饰器 模式，包装了 Slf4jLogger类的实现
 * <p>
 * Logger mLogger = LoggerFactory.getLogger("xxx")
 * <p>
 * Created by zhangyunfei on 15/9/17.
 */
public class LoggerDecorator implements ILog {
    private ILog mLoggerImpl;
    public static int LEVEL = Log.VERBOSE;
    private static boolean IS_ENABLE = true;
    private static SimpleDateFormat mSimpleDateFormat;

    public LoggerDecorator(ILog logger) {
        if (logger == null)
            throw new NullPointerException();
        mLoggerImpl = logger;
    }

    public static SimpleDateFormat getmSimpleDateFormat() {
        if (mSimpleDateFormat == null)
            mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return mSimpleDateFormat;
    }

    @Override
    public void debug(String log) {
        if (IS_ENABLE && LEVEL <= Log.DEBUG) {
            mLoggerImpl.debug(log);
        }
    }

    @Override
    public void info(String log) {
        if (IS_ENABLE && LEVEL <= Log.INFO) {
            mLoggerImpl.info(log);
        }
    }

    @Override
    public void error(String log) {
        if (IS_ENABLE && LEVEL <= Log.ERROR) {
            mLoggerImpl.error(log);
        }
    }

    @Override
    public void warn(String log) {
        if (IS_ENABLE && LEVEL <= Log.WARN) {
            mLoggerImpl.warn(log);
        }
    }

    public static void setEnable(boolean enable) {
        IS_ENABLE = enable;
    }

    public static void setLevel(int level) {
        LEVEL = level;
    }

}
