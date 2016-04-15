package com.peter.demo.activity.designpatterns.factorymethod;

/**
 * 加法操作类
 * Created by songzhongkun on 16/3/30 14:46.
 */
public class AddOperation extends Operation {

    @Override
    public int getResult() {
        return numberA + numberB;
    }
}
