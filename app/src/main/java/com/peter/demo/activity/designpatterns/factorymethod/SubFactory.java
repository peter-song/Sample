package com.peter.demo.activity.designpatterns.factorymethod;

/**
 * 减法工厂
 * Created by songzhongkun on 16/3/30 14:48.
 */
public class SubFactory extends Factory {

    @Override
    public Operation createOperation() {
        return new SubOperation();
    }
}
