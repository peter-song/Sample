package com.peter.common.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.peter.common.model.Advertising;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomePagerAdapter extends PagerAdapter {

	private List<Advertising> mBanners;
	private Context mContext;

	public HomePagerAdapter(Context context, List<Advertising> banners) {
		this.mContext = context;
		this.mBanners = banners;
	}

	@Override
	public int getCount() {
		if (mBanners != null) {
			return mBanners.size() <= 1 ? mBanners.size() : Integer.MAX_VALUE;// 设置成最大，使用户看不到边界
		} else {
			return 0;
		}
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		// 对ViewPager页号求模取出View列表中要显示的项
		position %= mBanners.size();
		if (position < 0) {
			position = mBanners.size() + position;
		}

		ImageView imageView = new ImageView(mContext);
		imageView.setScaleType(ScaleType.FIT_XY);
		Advertising banner = mBanners.get(position);
		if (banner.getImg_path() == null || "".equals(banner.getImg_path())) {
			imageView.setBackgroundResource(banner.getIcon());
		} else {
			Picasso.with(mContext).load(banner.getImg_path()).into(imageView);
		}
		// 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
		ViewParent vp = imageView.getParent();
		if (vp != null) {
			ViewGroup parent = (ViewGroup) vp;
			parent.removeView(imageView);
		}
		container.addView(imageView, 0);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// ((ViewPager) container).removeView((View) object);
	}

}
