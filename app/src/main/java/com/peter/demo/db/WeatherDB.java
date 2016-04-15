package com.peter.demo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.peter.demo.model.weather.City;
import com.peter.demo.model.weather.County;
import com.peter.demo.model.weather.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songzhongkun on 15/10/16.
 */
public class WeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "weather";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static WeatherDB weatherDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     *
     * @param context
     */
    private WeatherDB(Context context) {
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取WeatherDB的实例
     */
    public synchronized static WeatherDB getInstance(Context context) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    /**
     * 将省份信息存储到数据库
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.provinceName);
            values.put("province_code", province.provinceCode);
            db.insert("province", null, values);
        }
    }

    /**
     * 从数据库读取全国所有的省份信息
     */
    public List<Province> loadProvinces() {
        return loadProvinces(null);
    }

    /**
     * 从数据库读取全国所有的省份信息
     */
    public List<Province> loadProvinces(String limit) {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null, limit);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.id = cursor.getInt(cursor.getColumnIndex("id"));
                province.provinceName = cursor.getString(cursor.getColumnIndex("province_name"));
                province.provinceCode = cursor.getString(cursor.getColumnIndex("province_code"));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将城市信息存储到数据库
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.cityName);
            values.put("city_code", city.cityCode);
            values.put("province_id", city.provinceId);
            db.insert("City", null, values);
        }
    }

    /**
     * 根据省份id从数据库查询详细信息
     *
     * @param @param provinceId 设定文件
     * @return void 返回类型
     * @throws
     * @Title: getProvinceById
     */
    public Province getProvinceById(int provinceId) {
        Province province = new Province();
        Cursor cursor = db.query("Province", null, "id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            province.id = cursor.getInt(cursor.getColumnIndex("id"));
            province.provinceName = cursor.getString(cursor.getColumnIndex("province_name"));
            province.provinceCode = cursor.getString(cursor.getColumnIndex("province_code"));
        }
        return province;
    }

    /**
     * 根据城市编码获取城市信息
     *
     * @param @param  cityCode
     * @param @return 设定文件
     * @return City 返回类型
     * @throws
     * @Title: getCityByCityCode
     */
    public City getCityByCityCode(String cityCode) {
        City city = new City();
        Cursor cursor = db.query("City", null, "city_code=?", new String[]{String.valueOf(cityCode)}, null, null, null);
        if (cursor.moveToFirst()) {
            city.id = cursor.getInt(cursor.getColumnIndex("id"));
            city.cityName = cursor.getString(cursor.getColumnIndex("city_name"));
            city.cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
            city.provinceId = cursor.getInt(cursor.getColumnIndex("province_id"));
        }
        return city;
    }

    /**
     * 根据某省份信息从数据库查询该省下所有的城市信息
     */
    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.id = cursor.getInt(cursor.getColumnIndex("id"));
                city.cityName = cursor.getString(cursor.getColumnIndex("city_name"));
                city.cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
                city.provinceId = provinceId;
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 根据某城市信息从数据库查询该城市下的所有县信息
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.countyName);
            values.put("county_code", county.countyCode);
            values.put("city_id", county.cityId);
            db.insert("County", null, values);
        }
    }

    /**
     * 根据县编码获取县信息
     *
     * @param @param  cityCode
     * @param @return 设定文件
     * @return County 返回类型
     * @throws
     * @Title: getCountyByCityCode
     */
    public County getCountyByCityCode(String countyCode) {
        County county = new County();
        Cursor cursor = db.query("County", null, "county_code = ?", new String[]{String.valueOf(countyCode)}, null, null, null);
        if (cursor.moveToFirst()) {
            county.id = cursor.getInt(cursor.getColumnIndex("id"));
            county.countyName = cursor.getString(cursor.getColumnIndex("county_name"));
            county.countyCode = cursor.getString(cursor.getColumnIndex("county_code"));
            county.cityId = cursor.getInt(cursor.getColumnIndex("city_id"));
        }
        return county;
    }

    /**
     * 从数据库读取某城市下所有的县信息
     */
    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                County county = new County();
                county.id = cursor.getInt(cursor.getColumnIndex("id"));
                county.countyName = cursor.getString(cursor.getColumnIndex("county_name"));
                county.countyCode = cursor.getString(cursor.getColumnIndex("county_code"));
                county.cityId = cityId;
                list.add(county);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
