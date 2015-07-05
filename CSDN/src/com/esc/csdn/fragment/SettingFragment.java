package com.esc.csdn.fragment;

import java.util.ArrayList;
import java.util.List;

import org.netshull.csdn.R;

import com.esc.csdn.ACache;
import com.esc.csdn.CirclePro;
import com.esc.csdn.CirclePro.ClearData;
import com.esc.csdn.ListViewAdapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingFragment extends Fragment{
	
	/*private TextView aboutCsdn = null;
	private TextView clearData = null;*/
	
	private ListView mListView;
	private View mParentView;
	private View mConvertView;
	private List<String>mList=new ArrayList<String>();
	
	private CirclePro circlePro = null;
	private ToggleButton toggleButton = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mParentView = inflater.inflate(R.layout.mobile_setting,container,false);
		mConvertView=LayoutInflater.from(getActivity()).inflate(R.layout.mobile_setting_item, null);
		init(mParentView);
		return mParentView;
	}

	private void init(View parentView) {
		
		mListView=(ListView)mParentView.findViewById(R.id.settingsList);
		for(int i=0;i<getResources().getStringArray(R.array.csdn_about_array_1).length;i++){
			mList.add(getResources().getStringArray(R.array.csdn_about_array_1)[i]);
		}
		mListView.setAdapter(new ListViewAdapter(mList, getActivity(),R.layout.mobile_setting_item,R.id.TextItem));
		mListView.setOnItemClickListener(mListener);
		circlePro =  (CirclePro) parentView.findViewById(R.id.clear_data_pro);
		toggleButton = (ToggleButton) parentView.findViewById(R.id.toogleButton_id);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton button, boolean isChecked) {
				ACache.get(getActivity()).remove("checked");
				if (isChecked) {
					ACache.get(getActivity()).put("checked","save");
				}else {
					ACache.get(getActivity()).put("checked","notsave");
				}
			}
		});
		
		circlePro.setClearData(new ClearData() {
			
			@Override
			public void hide() {
//				Log.d("str","====================tset==============");
				Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.pro_hide);
				circlePro.startAnimation(animation);
				circlePro.setVisibility(View.GONE);
				Toast.makeText(getActivity(),"clear data success",1000).show();
				
				/**
				 * 这里写清楚缓存的代码，因为测试阶段比较耗费流量，先不写
				 */
			}
		});
		
		
	}
	private OnItemClickListener mListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (position) {
			case 0:
				startActivity(new Intent(getActivity(), AboutCsdnFragment.class));
				break;
			case 1:
				Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_LONG).show();
				break;
			case 2:
				circlePro.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}
	};
	

	/*@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.clear_data:
			circlePro.setVisibility(View.VISIBLE);
			
			break;
		case R.id.about_csdn:
			startActivity(new Intent(getActivity(), AboutCsdnFragment.class));

		default:
			break;
		}
	}*/
}
