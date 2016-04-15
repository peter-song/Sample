package com.peter.demo.activity.gif;

import com.peter.common.view.GifView;
import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

/**
 * Created by songzhongkun on 15/11/3 15:18.
 */
public class GifPlayFragment extends BaseFragment {
    private GifView gif_iv_orientation;

    @Override
    public int layoutId() {
        return R.layout.fragment_gifplay;
    }

    @Override
    public void initUI() {
        gif_iv_orientation = (GifView) findViewById(R.id.gif_iv_orientation);
    }

    @Override
    public void initData() {
        gif_iv_orientation.setMovieResource(R.drawable.delivery_confirm_arrows_right);
        gif_iv_orientation.setRotation(90f);
    }

    @Override
    public void initHeader() {
        getHeader().setTitle("Gif动画");
    }
}
