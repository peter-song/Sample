package com.peter.demo.net;

/**
 *
 * Created by songzhongkun on 15/11/5 12:19.
 */
public class Apis {

    public static final String CITY_ADDRESS_PREF = "http://www.weather.com.cn/data/list3/city";
    public static final String WEATHER_ADDRESS_PREF = "http://www.weather.com.cn/data/cityinfo/";

    // 服务地址：开发环境
    // 快递员app::216 柜体::232 测试::245/246/235
    private static final String SREVER_HOST = "http://10.0.1.216";
    private static final String USER_BASE_URL = SREVER_HOST + ":9000";

    private static final String SERVER_HOST2 = "http://121.42.157.166";
    private static final String USER_BASE_URL2 = SERVER_HOST2 + ":80";

    /**
     * 登录
     */
    public static final String LOGIN = USER_BASE_URL + "/capp/login/normal";

    /**
     * 登录
     */
    public static final String LOGIN2 = USER_BASE_URL2 + "/apply/user/login";

    /**
     * 注册
     */
    public static final String REGISTER  = USER_BASE_URL2 + "/apply/user/register";
}
