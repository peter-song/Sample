package com.peter.demo.dao;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import com.peter.demo.db.DataBaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by songzhongkun on 15/10/31 11:08.
 */
public abstract class BaseDao<T, Integer> {
    protected DataBaseHelper dataBaseHelper;
    protected Context context;

    public BaseDao(Context context) {
        this.context = context;
        getHelper();
    }

    public DataBaseHelper getHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = DataBaseHelper.getHelper(context);
        }
        return dataBaseHelper;
    }

    public void close() {
        if (dataBaseHelper == null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }

    public abstract Dao<T, Integer> getDao() throws SQLException;

    public int save(T t) {
        try {
            return getDao().create(t);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<T> query(String attributeName, String attributeValue) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        queryBuilder.where().eq(attributeName, attributeValue);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    public List<T> query(String[] attrbuteNames, String[] attrbuteValues) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        Where<T, Integer> wheres = queryBuilder.where();
        for (int i = 0; i < attrbuteNames.length; i++) {
            wheres.eq(attrbuteNames[i], attrbuteValues[i]);
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    public List<T> query(PreparedQuery<T> preparedQuery) {
        try {
            return getDao().query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> getAll() {
        try {
            return getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public T queryById(String idName, String idValue) throws SQLException {
        List<T> lst = query(idName, idValue);
        if (null != lst && !lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    public int update(T t) {
        try {
            return getDao().update(t);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(T t) {
        try {
            return getDao().delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void clearData(Class<T> tClass) {
        try {
            TableUtils.createTable(dataBaseHelper.getConnectionSource(), tClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
