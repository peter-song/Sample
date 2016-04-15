package com.peter.demo.activity.designpatterns.builder;

/**
 * 指挥者类
 * Created by songzhongkun on 16/3/30 13:31.
 */
public class Director {

    //构造产品
    public void construct(Builder builder) {
        builder.buildPart();
    }
}
