package com.peter.demo.config;

/**
 * Created by songzhongkun on 15/10/15.
 */

import java.text.SimpleDateFormat;

/**
 * 常量声明类
 *
 * @author peter
 * @ClassName: Constants
 * @date 2014-12-9 下午1:26:24
 */
public class Constants {

    /**
     * 语音类
     *
     * @author peter
     * @ClassName: Voice
     * @date 2015-1-15 上午11:58:07
     */
    public static class Voice {
        /**
         * 请输入手机号码和短信验证码
         */
        public static int LOGIN = 101;

        /**
         * 请选择可用箱体
         */
        public static int SELECT_BOX = 103;

        /**
         * 请选择充值方式
         */
        public static int SELECT_RECHARGE = 104;

        /**
         * 请输入取件密码
         */
        public static int INPUT_PASSWORD = 105;

        /**
         * 请扫描单号并输入收件人手机号码
         */
        public static int SAOMIAO = 107;

        /**
         * 请关闭箱门
         */
        public static int CLOSE_BOX = 108;
    }

    /**
     * 操作菜单类
     *
     * @author peter
     * @ClassName: OperationMenu
     * @date 2015-1-15 上午11:59:55
     */
    public static class IndexMenu {
        /**
         * 我要取件
         */
        public final static int INDEX_PICKUP = 1;
        /**
         * 我要投递
         */
        public final static int INDEX_DELIVERY = 2;
        /**
         * 我要寄存
         */
        public final static int INDEX_DEPOSIT = 3;
        /**
         * 我要充值
         */
        public final static int INDEX_RECHARGE = 4;
        /**
         * 我要查询
         */
        public final static int INDEX_INQUIRY = 5;
        /**
         * 用户须知
         */
        public final static int INDEX_HELP = 6;

    }

    /**
     * 登录类型类
     *
     * @author peter
     * @ClassName: LoginType
     * @date 2015-1-15 下午12:02:55
     */
    public static class LoginType {
        /**
         * 投递登录
         */
        public final static String DELIVERY = "delivery";

        /**
         * 寄存登录
         */
        public final static String DEPOSIT = "deposit";
    }

    /**
     * 格口类型类
     *
     * @author peter
     * @ClassName: BoxType
     * @date 2015-1-15 下午12:00:45
     */
    public static class BoxType {
        /**
         * 大箱
         */
        public final static String LARGE = "large";

        /**
         * 中箱
         */
        public final static String MIDDLE = "middle";

        /**
         * 小箱
         */
        public final static String SMALL = "small";

        /**
         * 超小箱
         */
        public final static String SMART = "smart";
    }

    /**
     * 日志类
     *
     * @author peter
     * @ClassName: Log
     * @date 2015-1-15 上午11:58:00
     */
    public static class Log {
        // 日志引擎 tag
        public static String LogTag = "[Cabinet]";
        public static int LOG_INFO = 0;
        public static int LOG_DEBUG = 1;
        public static int LOG_WARNING = 2;
        public static int LOG_ERROR = 3;
        public static int LOG_LEVEL = LOG_INFO;
        public static String logpath = "/box/Log/";
        public static String downloadPath = "/box/download/";
    }

    /**
     * 日期类
     *
     * @author peter
     * @ClassName: Date
     * @date 2015-1-15 上午11:57:47
     */
    public static class Date {
        // 日志引擎 日期格式化的格式
        public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        public static final SimpleDateFormat SDF3 = new SimpleDateFormat("HH:mm:ss");
        public static final SimpleDateFormat SDFSHOW = new SimpleDateFormat("MM-dd HH:mm");
        public static final SimpleDateFormat SDFBASE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        public static final SimpleDateFormat SDFBASE2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    }

    /**
     * 广告类型
     *
     * @author peter
     * @ClassName: AdvertisingType
     * @date 2015-1-16 下午9:08:02
     */
    public static class AdvertisingType {
        public static final int IMG = 1;
        public static final int VIDEO = 2;
    }
}
