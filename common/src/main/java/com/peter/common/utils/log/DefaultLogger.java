package com.peter.common.utils.log;

import android.util.Log;

/**
 * ILog 日志实现类
 * Created by wqr on 15/9/9.
 */
public class DefaultLogger implements ILog {

    public final static String TAG = "[core]" ;

    public DefaultLogger() {
    }

    @Override
    public void debug(String log) {
        Log.d(TAG, logInfo() + "【 " + log + " 】") ;
    }

    @Override
    public void info(String log) {
        Log.i(TAG, logInfo() + "【 " + log + " 】") ;
    }

    @Override
    public void error(String log) {
        Log.e(TAG, logInfo() + "【 " + log + " 】") ;
    }

    @Override
    public void warn(String log) {
        Log.w(TAG, logInfo() + "【 " + log + " 】") ;
    }

    private String logInfo() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length < 3) {
            return "Stack to shallow";
        } else {
            String fullClassName = elements[3].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = elements[3].getMethodName();
            int lineNumber = elements[3].getLineNumber();
            return className + "." + methodName + "():" + lineNumber;
        }
    }

}
