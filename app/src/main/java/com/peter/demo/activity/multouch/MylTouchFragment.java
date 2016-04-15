package com.peter.demo.activity.multouch;

import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

/**
 * Created by songzhongkun on 16/3/25 14:33.
 */
public class MylTouchFragment extends BaseFragment {

    private FrameLayout root;
    private ImageView iv_img;
    private TextView tv_event1;
    private TextView tv_event2;

    @Override
    public int layoutId() {
        return R.layout.fragment_mul_touch;
    }

    @Override
    public void initUI() {
        root = (FrameLayout) findViewById(R.id.root);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        tv_event1 = (TextView) findViewById(R.id.tv_event1);
        tv_event2 = (TextView) findViewById(R.id.tv_event2);
    }

    @Override
    public void initData() {
        root.setOnTouchListener(new View.OnTouchListener() {

            float currentDistance;
            float lastDistance = -1;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) iv_img.getLayoutParams();
                        if (event.getPointerCount() > 1) {
                            float offsetX = event.getX(0) - event.getX(1);
                            float offsetY = event.getY(0) - event.getY(1);

                            currentDistance = (float) Math.sqrt(offsetX * offsetX + offsetY * offsetY);
                            if (lastDistance < 0) {
                                lastDistance = currentDistance;
                            } else {
                                if (currentDistance - lastDistance > 5) {
                                    lp.width = (int) (1.1f * iv_img.getWidth());
                                    lp.height = (int) (1.1f * iv_img.getHeight());
                                }else if (lastDistance - currentDistance > 5){
                                    lp.width = (int) (0.9f * iv_img.getWidth());
                                    lp.height = (int) (0.9f * iv_img.getHeight());
                                }
                                lastDistance = currentDistance;
                                iv_img.setLayoutParams(lp);
                            }
                        }

                        lp.leftMargin = (int) event.getX();
                        lp.topMargin = (int) event.getY();
                        iv_img.setLayoutParams(lp);

                        tv_event1.setText(String.format("x1:%f y1:%f", event.getX(0), event.getY(0)));
                        if (event.getPointerCount() > 1) {
                            tv_event2.setText(String.format("x2:%f y2:%f", event.getX(1), event.getY(1)));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initHeader() {
        getHeader().setTitle("多点触摸交互处理");
    }
}
