package com.peter.common.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

/**
 * 日志组件的 外观类。请再使用前先调用 configureLogger 进行配置
 * Created by zhangyunfei on 15/9/17.
 */
public class Slf4jLogger implements ILog {
    private Logger mLogger;

    public Slf4jLogger(String tag) {
        mLogger = LoggerFactory.getLogger(tag == null ? Slf4jLogger.class.getSimpleName() : tag);
    }

    public Slf4jLogger(Class cls) {
        mLogger = LoggerFactory.getLogger(cls == null ? Slf4jLogger.class.getSimpleName() : cls.getSimpleName());
    }

    /**
     * 配置，日志组件。请再 主 apllication中的 onCreate中执行
     *
     * @param log_dir    日志文件的存放文件夹
     * @param filePrefix 日志文件前缀
     */
    public static void configureLogbackDirectly(String log_dir, String filePrefix) {
        if (log_dir == null)
            throw new NullPointerException();
        if (filePrefix == null)
            throw new NullPointerException();
        if (log_dir.endsWith("/")) {
            log_dir = log_dir.substring(0, log_dir.length() - 1);
        }
        // reset the default context (which may already have been initialized)
        // since we want to reconfigure it
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();


        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        rollingFileAppender.setAppend(true);
        rollingFileAppender.setContext(context);

        // OPTIONAL: Set an active log file (separate from the rollover files).
        // If rollingPolicy.fileNamePattern already set, you don't need this.
        //rollingFileAppender.setFile(LOG_DIR + "/log.txt");

        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
        rollingPolicy.setFileNamePattern(log_dir + "/" + filePrefix + "-%d{yyyy-MM-dd}.txt");
        rollingPolicy.setMaxHistory(7);
        rollingPolicy.setParent(rollingFileAppender);  // parent and context required!
        rollingPolicy.setContext(context);
        rollingPolicy.start();

        rollingFileAppender.setRollingPolicy(rollingPolicy);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        encoder.setContext(context);
        encoder.start();

        rollingFileAppender.setEncoder(encoder);
        rollingFileAppender.start();

        LogcatAppender logcatAppender = new LogcatAppender();
        logcatAppender.setContext(context);
        logcatAppender.setEncoder(encoder);
        logcatAppender.setName("logcat1");
        logcatAppender.start();

        // add the newly created appenders to the root logger;
        // qualify Logger to disambiguate from org.slf4j.Logger
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.TRACE);
        root.addAppender(rollingFileAppender);
        root.addAppender(logcatAppender);

        // print any status messages (warnings, etc) encountered in logback config
        //StatusPrinter.print(context);
    }


    @Override
    public void debug(String log) {
        mLogger.debug(log);
    }

    @Override
    public void info(String log) {
        mLogger.info(log);
    }

    @Override
    public void error(String log) {
        mLogger.error(log);
    }

    @Override
    public void warn(String log) {
        mLogger.warn(log);
    }

}
