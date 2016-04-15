package com.peter.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.common.R;

/**
 * 头部视图
 * Created by songzhongkun on 15/11/3 12:06.
 */
public class HeaderView extends LinearLayout {

    private LinearLayout ll_left;
    private LinearLayout ll_middle;
    private LinearLayout ll_right;
    private TextView tv_left;
    private TextView tv_middle;
    private TextView tv_right;
    private ImageView iv_left;
    private ImageView iv_right;

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.common_head, this);

        ll_left = (LinearLayout) findViewById(R.id.ll_left);
        ll_middle = (LinearLayout) findViewById(R.id.ll_middle);
        ll_right = (LinearLayout) findViewById(R.id.ll_right);

        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_middle = (TextView) findViewById(R.id.tv_middle);
        tv_right = (TextView) findViewById(R.id.tv_right);

        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
    }

    public void setLeftText(int id) {
        tv_left.setText(id);
    }

    public void setLeftText(String text) {
        tv_left.setText(text);
    }

    public void setTitle(int id) {
        tv_middle.setText(id);
    }

    public void setTitle(String text) {
        tv_middle.setText(text);
    }

    public void setRightText(int id) {
        tv_right.setText(id);
    }

    public void setRightText(String text) {
        tv_right.setText(text);
    }

    public void setLeftImage(int id) {
        iv_left.setImageResource(id);
    }

    public void setRightImage(int id) {
        iv_right.setImageResource(id);
    }

    public LinearLayout getLeftLayout() {
        return ll_left;
    }

    public LinearLayout getRightLayout() {
        return ll_right;
    }

    public void showLeftLayout() {
        ll_left.setVisibility(View.VISIBLE);
    }

    public void showRightLayout() {
        ll_right.setVisibility(View.VISIBLE);
    }

    public void hideLeftLayout() {
        ll_left.setVisibility(View.INVISIBLE);
    }

    public void hideRightLayout() {
        ll_right.setVisibility(View.INVISIBLE);
    }

    public void hideLeftAndRight() {
        hideLeftLayout();
        hideRightLayout();
    }

    public void showLeftText() {
        tv_left.setVisibility(View.VISIBLE);
        iv_left.setVisibility(View.GONE);
    }

    public void showLeftImg() {
        tv_left.setVisibility(View.GONE);
        iv_left.setVisibility(View.VISIBLE);
    }

    public void showRightText() {
        tv_right.setVisibility(View.VISIBLE);
        iv_right.setVisibility(View.GONE);
    }

    public void showRightImg() {
        tv_right.setVisibility(View.GONE);
        iv_right.setVisibility(View.VISIBLE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public void setOnLeftClickListener(OnClickListener leftClickListener) {
        ll_left.setClickable(true);
        ll_left.setOnClickListener(leftClickListener);
    }

    public void setOnRightClickListener(OnClickListener rightClickListener) {
        ll_right.setClickable(true);
        ll_right.setOnClickListener(rightClickListener);
    }

}
