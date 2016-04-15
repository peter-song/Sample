package com.peter.demo.model.word;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by songzhongkun on 15/10/31 15:27.
 */
@DatabaseTable(tableName = "word")
public class Word {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "English")
    public String english;
    @DatabaseField(columnName = "Chinese")
    public String chinese;
    @DatabaseField(columnName = "word_type")
    public int type;

    public Word() {
    }

    public Word(String english, String chinese, int type) {
        this.english = english;
        this.chinese = chinese;
        this.type = type;
    }
}
