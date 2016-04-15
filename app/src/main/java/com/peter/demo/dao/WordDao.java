package com.peter.demo.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.peter.demo.model.word.Word;

import java.sql.SQLException;

/**
 * Created by songzhongkun on 15/10/31 15:38.
 */
public class WordDao extends BaseDao<Word, Integer> {
    public WordDao(Context context) {
        super(context);
    }

    @Override
    public Dao<Word, Integer> getDao() throws SQLException {
        return dataBaseHelper.getDao(Word.class);
    }
}
