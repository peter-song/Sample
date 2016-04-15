package com.peter.demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * Created by songzhongkun on 15/10/16.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper {

    /**
     * Province表建表语句
     */
    public static final String CREATE_PROVINCE = "create table Province (id integer primary key autoincrement, province_name text, province_code text)";
    /**
     * City表建表语句
     */
    public static final String CREATE_CITY = "create table City (id integer primary key autoincrement, city_name text, city_code text, province_id integer)";
    /**
     * County表建表语句
     */
    public static final String CREATE_COUNTY = "create table County (id integer primary key autoincrement, county_name text, county_code text, city_id integer)";

    /**
     * 初始构造器
     *
     * @param context 上下文
     * @param name    数据库名
     * @param factory 游标工厂
     * @param version 数据库版本
     */
    public WeatherOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 第一次使用时调用,用于创建数据表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);// 创建省表
        db.execSQL(CREATE_CITY);// 创建市表
        db.execSQL(CREATE_COUNTY);// 创建县表
    }

    /**
     * 数据库版本发生变化时调用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
