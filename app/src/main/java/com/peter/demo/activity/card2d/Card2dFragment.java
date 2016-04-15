package com.peter.demo.activity.card2d;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

import butterknife.OnClick;

/**
 * 2d翻转
 * Created by songzhongkun on 16/3/24 17:22.
 */
public class Card2dFragment extends BaseFragment {

    private ImageView iv_imgA;
    private ImageView iv_imgB;

    private ScaleAnimation sato0 = new ScaleAnimation(1, 0, 1, 1, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
    private ScaleAnimation sato1 = new ScaleAnimation(0, 1, 1, 1, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);

    @Override
    public int layoutId() {
        return R.layout.fragment_card2d;
    }

    @Override
    public void initUI() {
        iv_imgA = (ImageView) findViewById(R.id.iv_imgA);
        iv_imgB = (ImageView) findViewById(R.id.iv_imgB);
    }

    @Override
    public void initData() {
        showImageA();
        sato0.setDuration(500);
        sato1.setDuration(500);

        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (iv_imgA.getVisibility() == View.VISIBLE) {
                    iv_imgA.setAnimation(null);
                    showImageB();
                    iv_imgB.startAnimation(sato1);
                } else {
                    iv_imgB.setAnimation(null);
                    showImageA();
                    iv_imgA.startAnimation(sato1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void initHeader() {
        getHeader().setTitle("2d翻转");
    }

    @OnClick(R.id.root)
    public void click(View v) {
        switch (v.getId()) {
            case R.id.root:
                if (iv_imgA.getVisibility() == View.VISIBLE) {
                    iv_imgA.startAnimation(sato0);
                } else {
                    iv_imgB.startAnimation(sato0);
                }
                break;
            default:
                break;
        }
    }

    private void showImageA() {
        iv_imgA.setVisibility(View.VISIBLE);
        iv_imgB.setVisibility(View.INVISIBLE);
    }

    private void showImageB() {
        iv_imgA.setVisibility(View.INVISIBLE);
        iv_imgB.setVisibility(View.VISIBLE);
    }
}
