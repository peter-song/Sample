package com.peter.demo.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动管理器
 * Created by songzhongkun on 15/10/15.
 */
public class ActivityCollector {
    //活动列表
    public static List<Activity> activities = new ArrayList<Activity>();

    //将活动添加到活动列表中
    public static void addActivity(Activity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
    }

    //从活动列表中移除活动
    public static void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
    }

    //关闭所有活动
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
