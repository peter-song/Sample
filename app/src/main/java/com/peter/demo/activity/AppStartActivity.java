package com.peter.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.peter.demo.R;

/**
 * App启动类
 * Created by songzhongkun on 15/10/15.
 */
public class AppStartActivity extends Activity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mImageView = (ImageView) findViewById(R.id.img_start);
        loadAnimation();
    }

    /**
     * 加载动画
     */
    private void loadAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        //监听动画过程
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(AppStartActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mImageView.setAnimation(animation);
    }
}
