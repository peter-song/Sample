package com.peter.demo.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.peter.demo.model.word.WordType;

import java.sql.SQLException;

/**
 * Created by songzhongkun on 15/10/31 15:39.
 */
public class WordTypeDao extends BaseDao<WordType, Integer> {
    public WordTypeDao(Context context) {
        super(context);
    }

    @Override
    public Dao<WordType, Integer> getDao() throws SQLException {
        return dataBaseHelper.getDao(WordType.class);
    }
}
