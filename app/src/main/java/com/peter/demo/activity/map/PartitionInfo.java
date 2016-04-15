package com.peter.demo.activity.map;

import java.io.Serializable;

/**
 * 分区（自提点）
 * Created by songzhongkun on 15/12/5 19:34.
 */
public class PartitionInfo implements Serializable {
    /**
     * 分区id
     */
    public int id;
    /**
     * 分区名称
     */
    public String name;
    /**
     * 分区地址
     */
    public String address;
    /**
     * 经度
     */
    public String longitude;
    /**
     * 纬度
     */
    public String latitude;

    @Override
    public String toString() {
        return "PartitionInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
