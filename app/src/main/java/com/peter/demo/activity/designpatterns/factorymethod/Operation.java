package com.peter.demo.activity.designpatterns.factorymethod;

/**
 * 抽象操作类
 * Created by songzhongkun on 16/3/30 14:44.
 */
public abstract class Operation {

    public int numberA;
    public int numberB;

    public abstract int getResult();
}
