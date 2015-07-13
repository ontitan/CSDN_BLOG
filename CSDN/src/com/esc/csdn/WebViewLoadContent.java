package com.esc.csdn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.netshull.csdn.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esc.csdn.dao.MobileTitleSave;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

@SuppressLint("JavascriptInterface")
public class WebViewLoadContent extends Activity implements OnTouchListener{
	String url = "";
	String title = "";
	WebView show = null;
	TextView loading = null;
	boolean hasSaved = false;
	DbUtils dbUtils = null;

	MyCircleView myCircleView = null;
	private ImageView mBackImageView=null;
	private ImageView mSave=null;
	private String[] mMenu_name;
	int titleIndex = 0;
	float touchYStart = 0f;
	float touchYEnd = 0f;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview_load_content);
		
		mMenu_name = getResources().getStringArray(R.array.menu_array);

		dbUtils = DbUtils.create(WebViewLoadContent.this);
		show = (WebView) findViewById(R.id.mobile_title_id);
		/*WebSettings mWebSettings=show.getSettings();
		mWebSettings.setUseWideViewPort(true);
		mWebSettings.setLoadWithOverviewMode(true);
		mWebSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		mWebSettings.setUseWideViewPort(true);*/
		myCircleView = (MyCircleView) findViewById(R.id.progress);
		mBackImageView=(ImageView)findViewById(R.id.go_back);
		mSave=(ImageView)findViewById(R.id.save);

		

		mSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MobileTitleSave saveEntity = new MobileTitleSave(url,title);
				if (hasSaved) {
					try {
						Log.d("test","=====start====");
						List<MobileTitleSave>list = dbUtils.findAll(MobileTitleSave.class);
						if (null != list && list.size() > 0) {
							for (MobileTitleSave mobileTitleSave : list) {
								Log.d("test",mobileTitleSave.getTitle());
								if (mobileTitleSave.getTitle().equals(title)) {
									dbUtils.delete(mobileTitleSave);
									break;
								}
							}
						}
						mSave.setBackgroundResource(R.drawable.no_collect);
						mSave.setVisibility(View.VISIBLE);
						hasSaved = false;
						Toast.makeText(WebViewLoadContent.this,"收藏取消",Toast.LENGTH_LONG).show();
					} catch (DbException e) {
						Toast.makeText(WebViewLoadContent.this,"取消收藏失败",Toast.LENGTH_LONG).show();
					}
				}else{
					try {
						
						dbUtils.save(saveEntity);
						mSave.setBackgroundResource(R.drawable.collect);
						mSave.setVisibility(View.VISIBLE);
						hasSaved = true;
						Toast.makeText(WebViewLoadContent.this,"收藏成功",Toast.LENGTH_LONG).show();
					} catch (DbException e) {
						Toast.makeText(WebViewLoadContent.this,"收藏失败",Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		mBackImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.title_in, R.anim.title_out);
			}
		});


		loading = (TextView) findViewById(R.id.loading);
		url = getIntent().getStringExtra("url");
		title = getIntent().getStringExtra("title");
		titleIndex = getIntent().getIntExtra("titleIndex",0);
		show.getSettings().setJavaScriptEnabled(true);
		
		show.addJavascriptInterface(new InnerClass(),"name");
		show.setWebViewClient(new WebViewClient(){
		});
		new MyAsyncTask().execute(new String());
	}	

	class MyAsyncTask extends AsyncTask<String,Integer,Void> {
		String html = "";
		@Override
		protected Void doInBackground(String... arg0) {
			Document childDoc;
			try {
				childDoc = Jsoup.connect(url).timeout(10000).get();
				html = childDoc.getElementsByAttributeValue("class","left").get(0).toString();
				/*html = html.replaceAll("<div class=\"page_nav\">[\\s\\S]*?<div id=\"comments\" class=\"csdn_comments\"></div> ", "");
				html = html.replaceAll("<div class=\"share\">[\\s\\S]*?</div>","");
				html = html.replaceAll(" <div class=\"digg\">[\\s\\S]*?</div>","");
				html = html.replaceAll("<div class=\"related\">[\\s\\S]*?</div>","");
				html = html.replaceAll("<div id=\"con_three_2\"[\\s\\S]*?</div>","");
				html = html.replaceAll("<div id=\"Tab3\" class=\"relational\"> ","");
				html = html.replaceFirst("<h2 class=\"tab_1 Menubox\">[\\s\\S]*?</h2>","");*/
				html = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html\"; charset=\"utf-8\"/></head><body>" + html;
				html = html + "</body></html>";
			} catch (Exception e) {
				html = download(url);
				html = html.replaceFirst("<table width=[\\s\\S]*?</table>","");
				}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			show.setWebViewClient(new WebViewClient(){//该设置是为了在点击webview 时候不打开系统浏览器
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});
			show.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
			myCircleView.setVisibility(View.GONE);
			loading.setText(mMenu_name[titleIndex]);
			loading.setVisibility(View.VISIBLE);
			try {
				List<MobileTitleSave>list = dbUtils.findAll(MobileTitleSave.class);
				if (null != list && list.size() > 0) {
					for (MobileTitleSave mobileTitleSave : list) {
						if (url.equals(mobileTitleSave.getTitleUrl())) {
							mSave.setBackgroundResource(R.drawable.collect);
							hasSaved = true;
							break;
						}
					}

				}
				if (!hasSaved) {
					mSave.setBackgroundResource(R.drawable.no_collect);
				}
				mSave.setVisibility(View.VISIBLE);

			} catch (DbException e) {
				e.printStackTrace();
			}
		}
	}

	class InnerClass {
		public void show(String str) {
			Log.d("str",str+"=------------------");

		}

	}


	public String download(String urlStr) {  
		StringBuffer sb = new StringBuffer();  
		String line = null;  
		BufferedReader buffer = null;  
		try {  
			URL url = new URL(urlStr);  
			HttpURLConnection urlConn = (HttpURLConnection) url  
			.openConnection(); 
			//            urlConn.setRequestProperty("user-agent", "Mozilla/5.0(Linux;U;Android 2.3;zh-CN;MI-ONEPlus)AppleWebKit/534.13(KHTML,like Gecko) UCBrowser/8.6.0.199U3/0.8.0Mobile Safari/534.13");
			buffer = new BufferedReader(new InputStreamReader(  
					urlConn.getInputStream()));  
			while ((line = buffer.readLine()) != null) {  
				sb.append(line);  
			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {  
			try {
				//            	if (buffer != null){
				buffer.close();  
				//            	}
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}  
		Log.d("test",sb.toString());
		return sb.toString();  
	}


	@Override
	public boolean onTouch(View view, MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchYStart = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			touchYEnd = event.getX();
			break;
		default:
			break;
		}
		Log.d("html","touchYStart="+touchYStart+"touchYEnd="+touchYEnd);
		if ((touchYEnd - touchYStart) < 50.f) {
			WebViewLoadContent.this.finish();
			overridePendingTransition(R.anim.title_in, R.anim.title_out);
		}
		
		return false;
	}  



}