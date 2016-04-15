package com.peter.demo.activity.recyclerview;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.peter.common.adapter.CommonRecyclerAdapter;
import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by songzhongkun on 15/11/9 12:14.
 */
public class RecyclerViewFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private CommonRecyclerAdapter mAdapter;

    @Override
    public int layoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    public void initUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
    }

    @Override
    public void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add("" + (char) i);
        }

        mAdapter = new CommonRecyclerAdapter<String>(mContext, mDatas, R.layout.item_single_textview) {
            @Override
            public void convert(MyViewHolder holder, String item) {
                holder.setText(R.id.tv_title, item);
                holder.setOnItemClickListener(R.id.ll_title, new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mAdapter.addData(position, "insert one");
                    }

                    @Override
                    public void OnItemLongClick(View view, int position) {
                        mAdapter.deleteData(position);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        //设置布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initHeader() {
        getHeader().setTitle("RecyclerView测试");
    }

    @OnClick({R.id.btn_listView, R.id.btn_gridView, R.id.btn_h_gridView, R.id.btn_staggered})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn_listView:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                break;
            case R.id.btn_gridView:
                mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                break;
            case R.id.btn_h_gridView:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
                break;
        }
    }
}
