package com.peter.demo.activity.designpatterns.factorymethod;

/**
 * 减法操作类
 * Created by songzhongkun on 16/3/30 14:46.
 */
public class SubOperation extends Operation {

    @Override
    public int getResult() {
        return numberA - numberB;
    }
}
