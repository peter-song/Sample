package com.peter.demo.activity.word;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.peter.common.adapter.CommonAdapter;
import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;
import com.peter.demo.dao.WordDao;
import com.peter.demo.dao.WordTypeDao;
import com.peter.demo.model.word.Word;
import com.peter.demo.model.word.WordType;

import java.util.List;

/**
 * Created by songzhongkun on 15/10/31 16:19.
 */
public class WordAddFragment extends BackHeaderFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText et_english;
    private EditText et_chinese;
    private Spinner sp_word_type;
    private Button btn_ok;
    private Button btn_reset;
    private Button btn_add_work_type;
    private WordTypeDao wordTypeDao;
    private WordDao wordDao;
    private CommonAdapter<WordType> mAdapter;
    private List<WordType> list;
    private int typeId;

    @Override
    public int layoutId() {
        return R.layout.fragment_word_add;
    }

    @Override
    public void initUI() {
        et_english = (EditText) findViewById(R.id.et_english);
        et_chinese = (EditText) findViewById(R.id.et_chinese);
        sp_word_type = (Spinner) findViewById(R.id.sp_word_type);
        sp_word_type.setOnItemSelectedListener(this);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_add_work_type = (Button) findViewById(R.id.btn_add_work_type);
        btn_ok.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        btn_add_work_type.setOnClickListener(this);
        wordTypeDao = new WordTypeDao(mContext);
        wordDao = new WordDao(mContext);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initHeader() {
        super.initHeader();
        getHeader().setTitle("添加单词");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                String english = et_english.getText().toString();
                String chinese = et_chinese.getText().toString();
                if (TextUtils.isEmpty(english) || TextUtils.isEmpty(chinese) || typeId == 0) {
                    alert("不能为空");
                } else {
                    wordDao.save(new Word(english, chinese, typeId));
                    alert("添加成功");
                    clearData();
                }
                break;
            case R.id.btn_reset:
                clearData();
                break;
            case R.id.btn_add_work_type:
                openNewFragment(new WordTypeAddFragment(), true);
                break;
        }
    }

    private void clearData() {
        et_english.setText("");
        et_chinese.setText("");
        typeId = 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        list = wordTypeDao.getAll();
        if (list == null) {
            return;
        }
        CommonAdapter<WordType> mAdapter = new CommonAdapter<WordType>(mContext, list, R.layout.item_word_type_list) {

            @Override
            public void convert(ViewHolder helper, WordType item) {
                helper.setText(R.id.tv_title, item.chinese);
            }
        };
        sp_word_type.setAdapter(mAdapter);
        sp_word_type.setSelection(list.size() - 1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        WordType wordType = list.get(position);
        typeId = wordType.id;
        parent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setVisibility(View.VISIBLE);
    }
}
