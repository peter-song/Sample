package com.peter.demo.activity.designpatterns.builder;

import android.content.Context;
import android.widget.Toast;

/**
 * 产品类，由多个部件组成
 * Created by songzhongkun on 16/3/30 13:20.
 */
public class Product {

    private String part;

    //添加产品部件
    public void setPart(String part) {
        this.part = part;
    }

    //显示所有产品信息
    public void show(Context context) {
        Toast.makeText(context, "创建该产品需要的部件为：" + part, Toast.LENGTH_SHORT).show();
    }
}
