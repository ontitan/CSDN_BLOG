package com.esc.csdn;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter{

	private List<View>views;
	
	public ViewPagerAdapter(List<View>views) {
		// TODO Auto-generated constructor stub
		this.views=views;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView(views.get(position%views.size()));
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.finishUpdate(container);
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		((ViewPager)container).addView(views.get(position), 0);
		return views.get(position);
				
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
		// TODO Auto-generated method stub
		super.restoreState(state, loader);
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return super.saveState();
	}

	@Override
	public void startUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.startUpdate(container);
	}
	
}
