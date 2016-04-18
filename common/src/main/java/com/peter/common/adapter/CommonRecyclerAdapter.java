package com.peter.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by songzhongkun on 15/11/9 13:57.
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    protected List<T> mDatas;
    private int mItemLayoutId;

    /**
     * 构造函数
     *
     * @param context      上下文对象
     * @param datas        填充数据（List集合）
     * @param itemLayoutId 布局文件
     */
    public CommonRecyclerAdapter(Context context, List<T> datas, int itemLayoutId) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = datas;
        this.mItemLayoutId = itemLayoutId;
    }

    /**
     * 构造函数
     *
     * @param context      上下文对象
     * @param datas        填充数据（Set集合）
     * @param itemLayoutId 布局文件
     */
    public CommonRecyclerAdapter(Context context, Set<T> datas, int itemLayoutId) {
        this.mContext = context;
        this.mDatas = new ArrayList<T>(datas);
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(mItemLayoutId, parent, false));
    }

    public abstract void convert(MyViewHolder holder, T item);

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int pos, T msg) {
        mDatas.add(pos + 1, msg);
        notifyItemInserted(pos + 1);
    }

    public void deleteData(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> mViews;
        private static View mConvertView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
            mConvertView = itemView;
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public MyViewHolder setText(int viewId, String text) {
            TextView view = getView(viewId);
            view.setText(text);
            return this;
        }

        public void setOnItemClickListener(final int viewId, final OnItemClickListener onItemClickListener) {
            if (onItemClickListener != null) {
                getView(viewId).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(getView(viewId), getLayoutPosition());
                    }
                });
                getView(viewId).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemClickListener.OnItemLongClick(getView(viewId), getLayoutPosition());
                        return false;
                    }
                });
            }
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void OnItemLongClick(View view, int position);

    }

}


