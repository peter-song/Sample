package com.peter.common.net;

import java.util.LinkedHashMap;

/**
 * Created by songzhongkun on 15/11/5 12:15.
 */
public class MyRequestParam extends LinkedHashMap<String, String> {
    private MyRequestParam() {

    }

    public static MyRequestParam create() {
        return new MyRequestParam();
    }

    public MyRequestParam with(String key, String value) {
        if (value != null) {
            put(key, value);
        }
        return this;
    }

    public MyRequestParam with(String key, int value) {
        put(key, "" + value);
        return this;
    }

    public MyRequestParam with(String key, long value) {
        put(key, "" + value);
        return this;
    }

    public MyRequestParam end() {
        return this;
    }

}
