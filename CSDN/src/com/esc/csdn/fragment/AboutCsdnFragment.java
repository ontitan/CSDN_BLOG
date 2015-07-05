package com.esc.csdn.fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.netshull.csdn.R;

import com.esc.csdn.ListViewAdapter;
import com.esc.csdn.ViewPagerAdapter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AboutCsdnFragment extends FragmentActivity implements OnPageChangeListener{
	
	private ListView mListView=null;
	private List<String>mList=new ArrayList<String>();
	private int i=0;
	
	private Boolean isGo=true;
	private Handler mHander;
	private Message mMessage;
	
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;
    private List<View>views;
    private ImageView[] dots;  
    private static final int []pics=new int[]{R.drawable.csdn1,R.drawable.csdn2,R.drawable.csdn3};
	
    private ImageView mSwitchBtn;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.about_csdn);
		mSwitchBtn=(ImageView)findViewById(R.id.back1);
		mSwitchBtn.setOnClickListener(mListener);
		mViewPager=(ViewPager)findViewById(R.id.mainViewPager);
		mListView=(ListView)findViewById(R.id.csdn_child);
		views=new ArrayList<View>();
		LinearLayout.LayoutParams mParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
		for(int i=0;i<pics.length;i++){
			ImageView view=new ImageView(this);
			view.setLayoutParams(mParams);
			view.setBackgroundResource(pics[i]);
			view.setScaleType(ScaleType.FIT_CENTER);
			views.add(view);
		}
		initBottomDots();
		mViewPagerAdapter=new ViewPagerAdapter(views);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setAdapter(mViewPagerAdapter);
		mHander=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(msg.arg1);
			}
		};
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isGo){
					try {
						mMessage=mHander.obtainMessage();
						mMessage.arg1=i;
						mHander.sendMessage(mMessage);
						Thread.sleep(3000);
						i++;
						if(i>2)i=0;
					} catch (InterruptedException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		for(int i=0;i<getResources().getStringArray(R.array.about_array).length;i++){
			mList.add(getResources().getStringArray(R.array.about_array)[i]);
		}
		mListView.setAdapter(new ListViewAdapter(mList, this,R.layout.about_csdn_item,R.id.listTextItem));
		
	}
	private OnClickListener mListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back1:
				finish();
				break;

			default:
				break;
			}
		}
	};
	private void initBottomDots() {
		// TODO Auto-generated method stub
		LinearLayout mlinLayout=(LinearLayout)findViewById(R.id.mainLinear);
		dots = new ImageView[pics.length];
		for(int i=0;i<dots.length;i++)
		{
			dots[i]=(ImageView)mlinLayout.getChildAt(i);
			dots[i].setEnabled(true);
			dots[i].setTag(i);
			
		}
		dots[0].setEnabled(false);
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		for(int i=0;i<dots.length;i++)
		{
			if(i==arg0)dots[i].setEnabled(false);
			else dots[i].setEnabled(true);
		}
	}
}
