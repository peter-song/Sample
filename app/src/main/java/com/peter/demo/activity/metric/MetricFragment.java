package com.peter.demo.activity.metric;

import android.util.DisplayMetrics;
import android.widget.TextView;

import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

/**
 * Created by songzhongkun on 15/11/3 15:34.
 */
public class MetricFragment extends BaseFragment {
    private TextView tv_widthPixels;
    private TextView tv_heightPixels;
    private TextView tv_density;
    private TextView tv_densityDpi;

    @Override
    public int layoutId() {
        return R.layout.fragment_metric;
    }

    @Override
    public void initUI() {
        tv_widthPixels = (TextView) findViewById(R.id.tv_widthPixels);
        tv_heightPixels = (TextView) findViewById(R.id.tv_heightPixels);
        tv_density = (TextView) findViewById(R.id.tv_density);
        tv_densityDpi = (TextView) findViewById(R.id.tv_densityDpi);
    }

    @Override
    public void initData() {
        DisplayMetrics metric = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        int height = metric.heightPixels;
        float density = metric.density;
        int densityDpi = metric.densityDpi;

        tv_widthPixels.setText(width + "");
        tv_heightPixels.setText(height + "");
        tv_density.setText(density + "");
        tv_densityDpi.setText(densityDpi + "");
    }

    @Override
    public void initHeader() {
        getHeader().setTitle("分辨率查看");
    }

}
