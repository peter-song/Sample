package com.peter.demo.activity.custommenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 自定义菜单界面
 * Created by songzhongkun on 16/3/25 10:49.
 */
public class MenuUI extends RelativeLayout {

    private FrameLayout leftMenu;
    private FrameLayout middleMenu;
    private FrameLayout rightMenu;
    private FrameLayout middleMask;
    private Scroller scroller;
    public static final int LEFT_ID = 0xaabbcc;
    public static final int MIDDLE_ID = 0xaaccbb;
    public static final int RIGHT_ID = 0xccbbaa;

    public MenuUI(Context context) {
        super(context);
        initView(context);
    }

    public MenuUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        scroller = new Scroller(context, new DecelerateInterpolator());
        leftMenu = new FrameLayout(context);
        middleMenu = new FrameLayout(context);
        rightMenu = new FrameLayout(context);
        middleMask = new FrameLayout(context);

        leftMenu.setBackgroundColor(Color.RED);
        middleMenu.setBackgroundColor(Color.GREEN);
        rightMenu.setBackgroundColor(Color.RED);
        middleMask.setBackgroundColor(0x88000000);

        leftMenu.setId(LEFT_ID);
        middleMenu.setId(MIDDLE_ID);
        rightMenu.setId(RIGHT_ID);

        addView(leftMenu);
        addView(middleMenu);
        addView(rightMenu);
        addView(middleMask);
        middleMask.setAlpha(0);
    }

    public float onMiddleMask() {
        return middleMask.getAlpha();
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        onMiddleMask();
        int curX = Math.abs(getScrollX());
        float scale = curX / (float) leftMenu.getMeasuredWidth();
        middleMask.setAlpha(scale);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        middleMenu.measure(widthMeasureSpec, heightMeasureSpec);
        middleMask.measure(widthMeasureSpec, heightMeasureSpec);

        int realWidth = MeasureSpec.getSize(widthMeasureSpec);
        int tempWidthMeasure = MeasureSpec.makeMeasureSpec((int) (realWidth * 0.8f), MeasureSpec.EXACTLY);
        leftMenu.measure(tempWidthMeasure, heightMeasureSpec);
        rightMenu.measure(tempWidthMeasure, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        middleMenu.layout(l, t, r, b);
        middleMask.layout(l, t, r, b);
        leftMenu.layout(l - leftMenu.getMeasuredWidth(), t, r, b);
        rightMenu.layout(l + middleMenu.getMeasuredWidth(), t, r + rightMenu.getMeasuredWidth(), b);
    }

    private boolean isTestCompete;
    private boolean isLeftRightEvent;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isTestCompete) {
            getEventType(ev);
            return true;
        }
        if (isLeftRightEvent) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_MOVE:
                    int cusScrollX = getScrollX();
                    int dis_x = (int) (ev.getX() - point.x);
                    int expectX = -dis_x + cusScrollX;
                    int finalX;
                    if (expectX < 0) {
                        finalX = Math.max(expectX, -leftMenu.getMeasuredWidth());
                    } else {
                        finalX = Math.min(expectX, rightMenu.getMeasuredWidth());
                    }
                    scrollTo(finalX, 0);
                    point.x = (int) ev.getX();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    cusScrollX = getScrollX();
                    if (Math.abs(cusScrollX) > leftMenu.getMeasuredWidth() >> 1) {
                        if (cusScrollX < 0) {
                            scroller.startScroll(cusScrollX, 0, -leftMenu.getMeasuredWidth() - cusScrollX, 0, 200);
                        } else {
                            scroller.startScroll(cusScrollX, 0, leftMenu.getMeasuredWidth() - cusScrollX, 0, 200);
                        }
                    } else {
                        scroller.startScroll(cusScrollX, 0, -cusScrollX, 0);
                    }
                    invalidate();
                    isLeftRightEvent = false;
                    isTestCompete = false;
                    break;
            }
        } else {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_UP:
                    isLeftRightEvent = false;
                    isTestCompete = false;
                    break;
                default:
                    break;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (!scroller.computeScrollOffset()) {
            return;
        }
        int tempx = scroller.getCurrX();
        scrollTo(tempx, 0);
    }

    private Point point = new Point();
    private static final int TEST_DIS = 20;

    private void getEventType(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                point.x = (int) ev.getX();
                point.y = (int) ev.getY();
                super.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) Math.abs(ev.getX() - point.x);
                int dy = (int) Math.abs(ev.getY() - point.y);
                if (dx >= TEST_DIS && dx > dy) {//左右滑动
                    isLeftRightEvent = true;
                    isTestCompete = true;
                    point.x = (int) ev.getX();
                    point.y = (int) ev.getY();
                } else if (dy >= TEST_DIS && dy > dx) {//上下滑动
                    isLeftRightEvent = false;
                    isTestCompete = true;
                    point.x = (int) ev.getX();
                    point.y = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                super.dispatchTouchEvent(ev);
                isLeftRightEvent = false;
                isTestCompete = false;
                break;
        }
    }
}
