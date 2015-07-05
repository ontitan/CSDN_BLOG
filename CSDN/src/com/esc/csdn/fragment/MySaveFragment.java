package com.esc.csdn.fragment;

import java.util.ArrayList;
import java.util.List;

import org.netshull.csdn.R;

import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.special.ResideMenu.ResideMenu;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuLayout;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.esc.csdn.MainFrame;
import com.esc.csdn.WebViewLoadContent;
import com.esc.csdn.dao.MobileTitleSave;

public class MySaveFragment extends Fragment implements OnTouchListener{
	private Activity mActivity;

	private View mLayoutView;

	private SwipeMenuListView swipeMenuListView = null;
	private MySaveAdapter mySaveAdapter = null;
	private List<MobileTitleSave>saveLists = new ArrayList<MobileTitleSave>();
	private DbUtils dbUtils = null;
	private LayoutInflater mLayoutInflater = null;
	
	private  MobileTitleSave saveEntity = null;

	RelativeLayout relativeLayout = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parentView = inflater.inflate(R.layout.my_save,container,false);
		ResideMenu resideMenu = ((MainFrame)getActivity()).getResideMenu();
		resideMenu.addIgnoredView(parentView);
		mActivity = getActivity();
		
		mLayoutView = parentView;
		init();
		parentView.setOnTouchListener(this);
		return parentView;
	}
	private void init() {
		dbUtils = DbUtils.create(mActivity);
		mLayoutInflater = LayoutInflater.from(mActivity);
		relativeLayout = (RelativeLayout) mLayoutView.findViewById(R.id.no_save_show);
		swipeMenuListView = (SwipeMenuListView) mLayoutView.findViewById(R.id.my_save_list);
		try {
			saveLists = dbUtils.findAll(MobileTitleSave.class);
			//			if (null != saveLists && saveLists.size() > 0) {
			//				for (MobileTitleSave mobileTitleSave : saveLists) {
			//					Log.d("test",mobileTitleSave.getTitle());
			//				}
			//			}
			
			if (null == saveLists || saveLists.size() == 0) {
				relativeLayout.setVisibility(View.VISIBLE);
			}else{
				mySaveAdapter = new MySaveAdapter();
				swipeMenuListView.setAdapter(mySaveAdapter);
			}
			
		} catch (DbException e) {
			e.printStackTrace();
		}

		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						mActivity);
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		//step 2
		swipeMenuListView.setMenuCreator(creator);
		//step3
		swipeMenuListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					try {
						dbUtils.delete(saveLists.get(position));
					} catch (DbException e) {
						e.printStackTrace();
					}
					saveLists.remove(position);
					mySaveAdapter.notifyDataSetChanged();
					if (saveLists.size() == 0) {
						relativeLayout.setVisibility(View.VISIBLE);
					}
					break;
				}
			}
		});


		swipeMenuListView.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
		
		
		swipeMenuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(mActivity,WebViewLoadContent.class);
				intent.putExtra("url",saveLists.get(position).getTitleUrl());
				intent.putExtra("title",saveLists.get(position).getTitle());
				intent.putExtra("titleIndex",1);
				mActivity.startActivity(intent);
				getActivity().overridePendingTransition(R.anim.other_in, R.anim.current_out); 
			}
		});
	}

	public class MySaveAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return saveLists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View container, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (null == container) {
				container = mLayoutInflater.inflate(R.layout.my_save_item,null);
				viewHolder = new ViewHolder();
				viewHolder.textTitle = (TextView) container.findViewById(R.id.save_id);
				container.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder) container.getTag();
			}
			viewHolder.textTitle.setText(saveLists.get(position).getTitle());
			return container;
		}

	}

	private class ViewHolder {
		TextView textTitle;
	}





	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return true;
	}


	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}


}
