package com.peter.demo.activity.scrollerview;

import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.peter.common.adapter.CommonAdapter;
import com.peter.common.adapter.CommonRecyclerAdapter;
import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 滚动条加列表展示
 * Created by songzhongkun on 15/12/7 11:16.
 */
public class ScrollerViewFragment extends BaseFragment {
    @Override
    public int layoutId() {
        return R.layout.fragment_scroller_view;
    }

    @Override
    public void initUI() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.myScrollView);
        scrollView.smoothScrollBy(0, 0);
    }

    @Override
    public void initData() {
        List<String> datas = new ArrayList<String>();
        datas.add("xiaoxin");
        datas.add("xiaoxin");
        datas.add("xiaoxin");
        datas.add("xiaoxin");
        datas.add("xiaoxin");
        datas.add("xiaoxin");
        datas.add("xiaoxin");
        datas.add("xiaoxin");
        datas.add("xiaoxin");

        CommonAdapter<String> adapter = new CommonAdapter<String>(mContext, datas, R.layout.item_single_textview) {
            @Override
            public void convert(ViewHolder holder, String item) {
                holder.setText(R.id.tv_title, item);
            }
        };

        CommonRecyclerAdapter<String> adapter1 = new CommonRecyclerAdapter<String>(mContext, datas, R.layout.item_single_textview) {
            @Override
            public void convert(MyViewHolder holder, String item) {
                holder.setText(R.id.tv_title, item);
            }
        };
        ListView listview = (ListView) findViewById(R.id.listview);
        RecyclerView listview2 = (RecyclerView) findViewById(R.id.listview2);
        GridView listview3 = (GridView) findViewById(R.id.listview3);

        listview.setAdapter(adapter);
        listview2.setAdapter(adapter1);
        listview2.setLayoutManager(new FullyGridLayoutManager(mContext, 3));
        listview3.setAdapter(adapter);
    }

    @Override
    public void initHeader() {
        getHeader().setTitle("ScollerView");
    }
}
