package com.peter.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.peter.common.R;

/**
 * Created by songzhongkun on 15/11/5 17:24.
 */
public class ToastUtils {
    public ToastUtils(Context context) {
        ToastMsg.BUILDER.init(context);
    }

    public enum ToastMsg {
        BUILDER;

        private Toast toast;
        private TextView tv;
        private View view;

        private void init(Context context) {
            view = LayoutInflater.from(context).inflate(
                    R.layout.toast, null);
            toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM, 0, px2dip(context, 120)); // 设置出现的位置
            toast.setDuration(Toast.LENGTH_LONG);
            // 自定义布局
            tv = (TextView) view.findViewById(R.id.tv_msg);
            toast.setView(view);
        }

        public void showToast(CharSequence text) {
            if (text.length() != 0) {
                tv.setText(text);
                toast.show();
            }
        }

        public void showToast(int id) {
            if (id != 0) {
                tv.setText(id);
                toast.show();
            }
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        private int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
    }

    public void toast(String text) {
        ToastMsg.BUILDER.showToast(text);
    }

    public void toast(int id) {
        ToastMsg.BUILDER.showToast(id);
    }
}
