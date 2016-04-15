package com.peter.demo.activity.designpatterns.builder;

/**
 * 具体建造者类1
 * Created by songzhongkun on 16/3/30 13:27.
 */
public class ConcreteBuilder2 extends Builder {

    private Product product = new Product();

    @Override
    public void buildPart() {
        product.setPart("部件B");
    }

    @Override
    public Product getRequest() {
        return product;
    }
}
