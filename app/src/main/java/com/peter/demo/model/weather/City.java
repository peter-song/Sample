package com.peter.demo.model.weather;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by songzhongkun on 15/10/16.
 */
@DatabaseTable(tableName = "City")
public class City {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "city_name")
    public String cityName;
    @DatabaseField(columnName = "city_code")
    public String cityCode;
    @DatabaseField(columnName = "province_id")
    public int provinceId;
}
