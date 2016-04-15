package com.peter.demo.activity.word;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.common.adapter.CommonAdapter;
import com.peter.common.utils.DialogUtil;
import com.peter.common.view.XListView;
import com.peter.demo.R;
import com.peter.demo.dao.WordDao;
import com.peter.demo.dao.WordTypeDao;
import com.peter.demo.model.word.Word;
import com.peter.demo.base.BaseFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songzhongkun on 15/10/31 15:43.
 */
public class WordListFragment extends BaseFragment implements XListView.IXListViewListener, View.OnClickListener, AdapterView.OnItemLongClickListener {

    private EditText et_word;
    private Button btn_add;
    private Button btn_search;
    private TextView tv_add;
    private XListView mListView;
    private LinearLayout ll_empty_data;
    private WordDao wordDao;
    private WordTypeDao wordTypeDao;
    private List<Word> list = new ArrayList<Word>();
    private CommonAdapter<Word> mAdapter;
    private Dialog dialog;

    @Override
    public int layoutId() {
        return R.layout.fragment_word_list;
    }

    @Override
    public void initUI() {
        et_word = (EditText) findViewById(R.id.et_word);
        btn_add = (Button) findViewById(R.id.btn_add);
        tv_add = (TextView) findViewById(R.id.tv_add);
        btn_search = (Button) findViewById(R.id.btn_search);
        mListView = (XListView) findViewById(R.id.xListView);
        mListView.setOnItemLongClickListener(this);
        ll_empty_data = (LinearLayout) findViewById(R.id.ll_empty_data);
        btn_add.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        wordDao = new WordDao(mContext);
        wordTypeDao = new WordTypeDao(mContext);

        // 设置下拉以及加载更多监听事件
        mListView.setXListViewListener(this);
        // 设置是否可以下拉刷新
        mListView.setPullRefreshEnable(true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initHeader() {
        getHeader().setTitle("单词");
        getHeader().hideLeftAndRight();
    }

    @Override
    public void onResume() {
        super.onResume();
        list = wordDao.getAll();
        loadDate();
    }

    private void loadDate() {
        if (list.size() == 0) {
            mListView.setVisibility(View.GONE);
            ll_empty_data.setVisibility(View.VISIBLE);
        } else {
            ll_empty_data.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }

        if (list.size() >= 10) {
            mListView.setPullLoadEnable(true);
        } else {
            mListView.setPullLoadEnable(false);
        }

        mListView.setAdapter(mAdapter = new CommonAdapter<Word>(mContext, list, R.layout.item_word_list) {
            @Override
            public void convert(ViewHolder helper, Word item) {
                helper.setText(R.id.tv_english, item.english);
                helper.setText(R.id.tv_chinese, item.chinese);
                try {
                    helper.setText(R.id.tv_type, (wordTypeDao.queryById("id", item.type + "")).chinese);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        loadDate();
        onLoad();
    }

    @Override
    public void onLoadMore() {
        alert("onLoadMore");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
            case R.id.tv_add:
                openNewFragment(new WordAddFragment(), true);
                break;
            case R.id.btn_search:
                String word = et_word.getText().toString().trim();
                try {
                    if (TextUtils.isEmpty(word))
                        list = wordDao.getAll();
                    else
                        list = wordDao.query("english", word);
                    loadDate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        dialog = new DialogUtil().createDialog(mContext, "操作提示", "确定要删除该条记录吗？", new DialogUtil.OnButtonListener() {
            @Override
            public void okButton() {
                Word word = list.get(position - 1);
                list.remove(word);
                wordDao.delete(word);
                alert("删除成功");
                if (list.size() == 0) {
                    mListView.setVisibility(View.GONE);
                    ll_empty_data.setVisibility(View.VISIBLE);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void cancelButton() {
                dialog.dismiss();
            }
        });
        return false;
    }

    /**
     * 停止加载数据
     */
    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getResources().getString(R.string.justNow));
    }
}
