package com.peter.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期辅助类
 * Created by songzhongkun on 15/10/15.
 */
public class DateUtils {

    private DateUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将字符串时间按照指定的时间格式进行转换
     *
     * @param time       字符串时间
     * @param timeFormat 时间格式
     * @return
     */
    public static String dateToString(String time, String timeFormat) {
        String newTime = "";
        if (time != null && !time.equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
            newTime = formatter.format(format(time));
        }
        return newTime;
    }

    /**
     * 将字符串格式时间转换为日期格式时间
     *
     * @param date
     * @return
     */
    public static Date format(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null || "null".equals(date.trim()) || "".equals(date.trim())) {
            return null;
        }
        Date date2 = null;
        try {
            date2 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }

    /**
     * 根据时间格式获取当前时间 默认为yyyy-MM-dd HH:mm:ss格式
     *
     * @return
     */
    public static String getNowTime() {
        return getNowTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据时间格式获取当前时间
     *
     * @param timeFormat
     * @return
     */
    public static String getNowTime(String timeFormat) {
        DateFormat df = new SimpleDateFormat(timeFormat);
        Calendar c = Calendar.getInstance();
        return df.format(c.getTime());
    }

    /**
     * 比较前一个时间是否大于后一时间
     *
     * @param date1
     * @param date2
     * @return true false
     */
    public static boolean isDateBefore(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取上月有多少天
     *
     * @return
     */
    public static int getLastMonthDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return c.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取时间值 默认为当天
     *
     * @return
     */
    public static int getValue() {
        return getValue(getNowTime(), "day");
    }

    /**
     * 根据类型获取时间值 默认为当天
     *
     * @param type
     * @return
     */
    public static int getValue(String type) {
        return getValue(getNowTime(), type);
    }

    /**
     * 根据时间和类型获取时间值值
     *
     * @param time
     * @param type
     * @return
     */
    public static int getValue(String time, String type) {
        int value = 0;
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("时间转换出错");
        }

        if (type.equalsIgnoreCase("day")) {
            value = c.get(Calendar.DATE);
        } else if (type.equalsIgnoreCase("week")) {
            value = c.get(Calendar.DAY_OF_WEEK) - 1;
            if (value == 0) {
                value = 7;
            }
        } else if (type.equalsIgnoreCase("month")) {
            value = c.get(Calendar.MONTH) + 1;
        } else if (type.equalsIgnoreCase("year")) {
            value = c.get(Calendar.YEAR);
        }

        return value;
    }

    /**
     * 获取时间差
     *
     * @param time_Format 时间格式
     * @param start_Time  开始时间
     * @param end_Time    截止时间
     * @return
     */
    public static Long getTimeInterval(String time_Format, String start_Time, String end_Time, char type) {
        Long count = 0l;
        SimpleDateFormat dfs = new SimpleDateFormat(time_Format);
        long between = 0;
        try {
            Date begin = dfs.parse(start_Time);
            Date end = dfs.parse(end_Time);
            between = (end.getTime() - begin.getTime());// 得到两者的秒差数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long seconds = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        System.out.println(day + "天" + hour + "小时" + min + "分" + seconds + "秒");

        switch (type) {
            case 'd':
                count = between / (24 * 60 * 60 * 1000);
                if (between % (24 * 60 * 60 * 1000) > 0) {
                    count++;
                }
                break;
            case 'h':
                count = between / (60 * 60 * 1000);
                if (between % (60 * 60 * 1000) > 0) {
                    count++;
                }
                break;
            case 'm':
                count = between / (60 * 1000);
                if (between % (60 * 1000) > 0) {
                    count++;
                }
                break;
            case 's':
                count = between / (1000);
                if (between % (1000) > 0) {
                    count++;
                }
                break;
        }
        return count;
    }
}
