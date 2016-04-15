package com.peter.demo.activity.word;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;
import com.peter.demo.dao.WordTypeDao;
import com.peter.demo.model.word.WordType;

/**
 * Created by songzhongkun on 15/10/31 16:29.
 */
public class WordTypeAddFragment extends BackHeaderFragment implements View.OnClickListener {
    private EditText et_english;
    private EditText et_chinese;
    private Button btn_ok;
    private Button btn_reset;

    private WordTypeDao wordTypeDao;

    @Override
    public int layoutId() {
        return R.layout.fragment_word_type_add;
    }

    @Override
    public void initUI() {
        et_english = (EditText) findViewById(R.id.et_english);
        et_chinese = (EditText) findViewById(R.id.et_chinese);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_ok.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        wordTypeDao = new WordTypeDao(mContext);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initHeader() {
        super.initHeader();
        getHeader().setTitle("添加类型");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                String english = et_english.getText().toString();
                String chinese = et_chinese.getText().toString();
                if (TextUtils.isEmpty(english) || TextUtils.isEmpty(chinese)) {
                    alert("不能为空");
                } else {
                    wordTypeDao.save(new WordType(english, chinese));
                    alert("添加成功");
                    closeTopFragment();
                }
                break;
            case R.id.btn_reset:
                et_english.setText("");
                et_chinese.setText("");
                break;
        }
    }
}
