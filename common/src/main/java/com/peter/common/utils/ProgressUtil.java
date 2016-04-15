package com.peter.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.common.R;

/**
 * 自定义圆形加载中进度条
 * Created by songzhongkun on 15/10/21 14:37.
 */
public class ProgressUtil extends Dialog {

    private Context context;
    private ImageView spaceshipImage;

    public ProgressUtil(Context context) {
        this(context, "");
    }

    public ProgressUtil(Context context, String msg) {
        this(context, R.style.CustomProgressDialog, msg);
    }

    public ProgressUtil(Context context, int themeResId, String msg) {
        super(context, themeResId);
        this.context = context;
        setContentView(R.layout.loading_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;

        //图片
        spaceshipImage = (ImageView) findViewById(R.id.img);

        // 提示文字
        TextView tipTextView = (TextView) findViewById(R.id.tipTextView);
        if (TextUtils.isEmpty(msg)) {
            tipTextView.setVisibility(View.GONE);
        } else {
            tipTextView.setText(msg);
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.dialog_view);// 加载布局
        setCancelable(false);
        setContentView(layout, new LinearLayout.LayoutParams(
                300, 200));
    }

    @Override
    public void show() {
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.load_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        super.show();
    }

}
