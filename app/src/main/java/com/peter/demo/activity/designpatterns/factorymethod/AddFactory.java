package com.peter.demo.activity.designpatterns.factorymethod;

/**
 * 加法工厂
 * Created by songzhongkun on 16/3/30 14:48.
 */
public class AddFactory extends Factory {

    @Override
    public Operation createOperation() {
        return new AddOperation();
    }
}
