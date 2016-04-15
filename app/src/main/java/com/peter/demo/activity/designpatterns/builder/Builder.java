package com.peter.demo.activity.designpatterns.builder;

/**
 * 抽象建造者类， 确定产品由两个部件partA和partB组成，并声明一个得到产品建造者的方法getRequest
 * Created by songzhongkun on 16/3/30 13:25.
 */
public abstract class Builder {
    public abstract void buildPart();
    public abstract Product getRequest();
}
