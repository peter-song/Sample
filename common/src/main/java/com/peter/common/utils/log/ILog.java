package com.peter.common.utils.log;

/**
 * 日志接口
 * Created by wqr on 15/9/9.
 */
public interface ILog {

    void debug(String log);

    void info(String log);

    void error(String log);

    void warn(String log);
}