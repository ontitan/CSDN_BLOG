package com.esc.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{
	private List<String>mList=null;
	private Context mContext;
	private int mFlag;
	private int mFlag1;
	
	public ListViewAdapter(List<String>list,Context context,int flag,int flag1) {
		// TODO Auto-generated constructor stub
		this.mList=list;
		this.mContext=context;
		this.mFlag=flag;
		this.mFlag1=flag1;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(null==convertView){
			//convertView=LayoutInflater.from(mContext).inflate(R.layout.csdn_info_below_item, null);
			convertView=LayoutInflater.from(mContext).inflate(mFlag, null);
		}
		TextView mTextView=(TextView)convertView.findViewById(mFlag1);
		mTextView.setText(mList.get(position));
		return convertView;
	}
}
