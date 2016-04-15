package com.peter.common.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;

import com.peter.common.utils.log.ILog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wqr
 * @ClassName: DeviceUtil
 * 设备参数相关 工具类
 * @date 2014-11-5 上午2:55:16
 */
public class DeviceUtil {

    private static final ILog logger = LogUtils.getLogger("DeviceUtil");

    public static void printDeviceInfo(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        float density = metric.density;
        float densityDpi = metric.densityDpi;
        int heightPixels = metric.heightPixels;
        float scaledDensity = metric.scaledDensity;
        float xdpi = metric.xdpi;
        float ydpi = metric.ydpi;
        logger.info("density " + density + ",densityDpi " + densityDpi
                + ", densityDpi " + densityDpi + ", heightPixels " + heightPixels
                + ", scaledDensity " + scaledDensity + ", xdpi " + xdpi + ", ydpi " + ydpi);
    }

    /**
     * get http request UserAgent
     *
     * @param context
     * @return
     */
    public static String getUserAgent(Context context) {
        return new WebView(context).getSettings().getUserAgentString();
    }

    /**
     * get IMSI
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();

        return imsi;
    }

    /**
     * get IMEI
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();

        if (imei == null || imei.length() <= 0) {

            try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);

                imei = (String) get.invoke(c, "ro.serialno");
            } catch (SecurityException e) {
                logger.info(e.getLocalizedMessage());
            } catch (IllegalArgumentException e) {
                logger.info(e.getLocalizedMessage());
            } catch (ClassNotFoundException e) {
                logger.info(e.getLocalizedMessage());
            } catch (NoSuchMethodException e) {
                logger.info(e.getLocalizedMessage());
            } catch (IllegalAccessException e) {
                logger.info(e.getLocalizedMessage());
            } catch (InvocationTargetException e) {
                logger.info(e.getLocalizedMessage());
            }

        }

        return imei;
    }

    public static String getPN() {
        return Build.MODEL;
    }

    /**
     * get sdk version
     *
     * @return
     */
    public static int getSdkversion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * get width*hight
     *
     * @param context
     * @return
     */
    public static String getScreenSize(Context context) {
        return getScreenWidth(context) + "*" + getScreenHight(context);
    }

    /**
     * get Screen Width
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDisplay(context).getWidth();
    }

    /**
     * get Screen Hight
     *
     * @param context
     * @return
     */
    public static int getScreenHight(Context context) {
        return getDisplay(context).getHeight();
    }

    /**
     * get Display
     *
     * @param context
     * @return
     */
    private static Display getDisplay(Context context) {
        Display display = ((WindowManager) context.getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display;
    }
}
