package com.esc.csdn.fragment;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.netshull.csdn.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esc.csdn.ACache;
import com.esc.csdn.MainFrame;
import com.esc.csdn.WebViewLoadContent;
import com.esc.csdn.dao.MobileDao;
import com.esc.csdn.entity.SoftDevEntity;
import com.esc.csdn.utils.NetUtil;
import com.esc.csdn.utils.TimeUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.special.ResideMenu.ResideMenu;

public class SoftDevFragment extends Fragment implements IXListViewRefreshListener,IXListViewLoadMore,OnTouchListener{
	private XListView mListView = null;
	private MobileAdapter mobileAdapter = null;
	private List<SoftDevEntity>mSoftDevEntityList = new ArrayList<SoftDevEntity>();
	private LayoutInflater mLayoutInflater = null;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	private DbUtils dbUtils = null;


	private int currentPage = 2;

	private ACache cache = null;
	private static final String TAG = "Mobile";
	
	private Activity mActivity;
	
	private View mLayoutView;
	private View parentView = null;
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        parentView = inflater.inflate(R.layout.mobile_xlistview_layout, container, false);
	        ResideMenu resideMenu = ((MainFrame)getActivity()).getResideMenu();
	        ((ViewGroup)(getActivity().findViewById(android.R.id.content))).getChildAt(0).setBackgroundColor(Color.parseColor("#121111"));
	        resideMenu.addIgnoredView(parentView);
	        mActivity = getActivity();
	        mLayoutView = parentView;
	        init();
	        
	        parentView.setOnTouchListener(this);
			return parentView;
	    }

	
	private void init() {
		cache = ACache.get(mActivity);
		mLayoutInflater = LayoutInflater.from(mActivity);

		imageLoader.init(ImageLoaderConfiguration.createDefault(mActivity));
		dbUtils = DbUtils.create(mActivity);

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_on_loading)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(10))
		.build();
		
		mSoftDevEntityList = new ArrayList<SoftDevEntity>();
		mListView = (XListView) mLayoutView.findViewById(R.id.mobile_listview);
		mSoftDevEntityList = new ArrayList<SoftDevEntity>();
		mobileAdapter = new MobileAdapter();
		mListView.setAdapter(mobileAdapter);


		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				//				Log.i(TAG,mobile_list.get(position).getTitle());
				Intent intent = new Intent(mActivity,WebViewLoadContent.class);
				intent.putExtra("url",mSoftDevEntityList.get(position-1).getTitleUrl());
				intent.putExtra("title",mSoftDevEntityList.get(position-1).getTitle());
				intent.putExtra("titleIndex",4);
				mActivity.startActivity(intent);
				getActivity().overridePendingTransition(R.anim.other_in, R.anim.current_out); 
			}
		});

		mListView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View position) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		mListView.setPullRefreshEnable(this);
		mListView.setPullLoadEnable(this);
		mListView.NotRefreshAtBegin();
		mSoftDevEntityList = new MobileDao(mActivity).getSaveSoftDev();
		if (null == mSoftDevEntityList || mSoftDevEntityList.size() == 0) {
			mSoftDevEntityList = new ArrayList<SoftDevEntity>();
			mListView.startRefresh();
		}
//		mListView.startRefresh();
	}




	private class MobileAdapter extends BaseAdapter {
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return null == mSoftDevEntityList ? 0 :mSoftDevEntityList.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHolder;
			if (null == convertView) {
				convertView = mLayoutInflater.inflate(R.layout.mobile_xlistview_item,parent,false);
				viewHolder = new ViewHolder();
				viewHolder.mTitle = (TextView) convertView.findViewById(R.id.mobile_title);
				viewHolder.mImage = (ImageView) convertView.findViewById(R.id.mobile_image);
				viewHolder.mContent = (TextView) convertView.findViewById(R.id.mobile_content);
				viewHolder.mPubTime = (TextView) convertView.findViewById(R.id.mobile_pub_time);
				viewHolder.mReadCount = (TextView) convertView.findViewById(R.id.mobile_read_count);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (null != mSoftDevEntityList && mSoftDevEntityList.size() > 0) {
				viewHolder.mTitle.setText(mSoftDevEntityList.get(position).getTitle());
				viewHolder.mContent.setText(mSoftDevEntityList.get(position).getContent());
				viewHolder.mPubTime.setText(mSoftDevEntityList.get(position).getPubTime());
				viewHolder.mReadCount.setText(mSoftDevEntityList.get(position).getReadCount());
				final String image_url = mSoftDevEntityList.get(position).getPicUrl();
				if (null == image_url || image_url.equals("")) {
					convertView.findViewById(R.id.mobile_image).setVisibility(View.GONE);
				}else {
					convertView.findViewById(R.id.mobile_image).setVisibility(View.VISIBLE);
				}
				
				ImageLoader.getInstance().displayImage(image_url, viewHolder.mImage, options, animateFirstListener);

			}else{
				if (!NetUtil.checkNet(mActivity)) {
					Toast.makeText(mActivity, "please check the network link",1000).show();
				}else{
					Toast.makeText(mActivity, "no data more",1000).show();
				}

			}
			return convertView;

		}

	}

	private class ViewHolder {

		TextView mTitle;
		ImageView mImage;
		TextView mContent;
		TextView mPubTime;
		TextView mReadCount;

	}

	class MyAsyncTask extends AsyncTask<String,Integer,Void> {
		private List<SoftDevEntity>cacheList = null;
		boolean isExit = false;
		@Override
		protected Void doInBackground(String... url){
			cache.put("SOFT",TimeUtils.getCurrentTime());
			boolean isConnected = NetUtil.checkNet(mActivity);

			if (!isConnected) {
				mSoftDevEntityList = new MobileDao(mActivity).getSaveSoftDev();
			}else{ 
				String isTag = "";
				Document doc;
				try {
					doc = Jsoup.connect(url[0]).userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; .NET CLR 1.1.4322)").timeout(10000).get();

					Element leftDiv = doc.getElementsByAttributeValue("id","ddimagetabs").get(0);
					String title = "";
					String titleUrl = "";
					String pubTime = "";
					String readCount = "";
					String commentCount = "";
					String picUrl = "";
					String content = "";
					List<String>tags = new ArrayList<String>();
					Element contentDiv = doc.getElementsByAttributeValue("class","news").get(0);
					//						System.out.println(contentDiv);
					Elements contents = contentDiv.getElementsByAttributeValue("class","unit");
					SoftDevEntity cloudEntity = null;
					for (Element element : contents) {
						tags.clear();
						title = element.getElementsByTag("a").get(0).text();
						titleUrl = element.getElementsByTag("a").get(0).attr("href");
						pubTime = element.getElementsByAttributeValue("class","ago").get(0).text();
						readCount = element.getElementsByAttributeValueContaining("class","view_time").get(0).text();
						commentCount = element.getElementsByAttributeValueContaining("class","num_recom").get(0).text();
						try {
							picUrl = element.getElementsByTag("img").get(0).attr("src");
						} catch (Exception e) {
							picUrl = "";
						}
						content = element.getElementsByTag("dd").get(0).text();
						Elements tagElements = element.getElementsByAttributeValue("class","tag").get(0).getElementsByTag("a");
						for (Element element2 : tagElements) {
							tags.add(element2.text());
						}
						cloudEntity = new SoftDevEntity(title,titleUrl, pubTime, readCount, commentCount, picUrl, content, tags);


						cacheList = new MobileDao(mActivity).getSaveSoftDev();
						if (null != cacheList && cacheList.size() > 0) {
							for (SoftDevEntity entity : cacheList) {
								if (entity.getTitleUrl().equals(cloudEntity.getTitleUrl())) {
									isExit = true;
									break;
								} 
							}
						}
						if (!isExit) {
							try {
								try {
									isTag = url[1];
								} catch (Exception e) {
									Log.d("html",isTag);
									if (null != isTag && "isrefresh".equals(isTag)) {
										mSoftDevEntityList = dbUtils.findAll(SoftDevEntity.class);
										if (null != mSoftDevEntityList) {
											mSoftDevEntityList.add(0, cloudEntity);
											dbUtils.delete(SoftDevEntity.class);
											dbUtils.saveAll(mSoftDevEntityList);
										}
									}else {
										dbUtils.save(cloudEntity);
										mSoftDevEntityList.add(cloudEntity);
									}
								}

							} catch (DbException e) {
								e.printStackTrace();
							}
						}

					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mobileAdapter.notifyDataSetChanged();
			parentView.findViewById(R.id.progressfresh).setVisibility(View.GONE);
			mListView.stopRefresh();
			mListView.stopLoadMore();
			super.onPostExecute(result);
		}
	}



	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 200);
					displayedImages.add(imageUri);
				}
			}
		}
	}



	@Override
	public void onLoadMore() {
		new MyAsyncTask().execute(new String[]{"http://sd.csdn.net/sd/"+currentPage++,"isloadmore"});
	}
	@Override
	public void onRefresh() {
		if (null == cache.getAsString("SOFT")) {
			mListView.setRefreshTime("第一次刷新");
			parentView.findViewById(R.id.progressfresh).setVisibility(View.VISIBLE);
			new MyAsyncTask().execute(new String[]{"http://sd.csdn.net/"});

		}else{
			mListView.setRefreshTime(cache.getAsString("lastrefresh"));
			new MyAsyncTask().execute(new String[]{"http://sd.csdn.net/","isrefresh"});
		}

	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return true;
	}



}
