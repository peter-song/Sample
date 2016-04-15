package com.peter.demo.model.word;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by songzhongkun on 15/10/31 15:33.
 */
@DatabaseTable(tableName = "word_type")
public class WordType {
    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = "English")
    public String english;
    @DatabaseField(columnName = "Chinese")
    public String chinese;

    public WordType() {
    }

    public WordType(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    public WordType(int id, String english, String chinese) {
        this.id = id;
        this.english = english;
        this.chinese = chinese;
    }
}
