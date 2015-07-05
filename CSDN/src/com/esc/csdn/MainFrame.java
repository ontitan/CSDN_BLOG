package com.esc.csdn;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.netshull.csdn.R;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esc.csdn.fragment.CloudFragment;
import com.esc.csdn.fragment.IndustryFragment;
import com.esc.csdn.fragment.MagzineFragment;
import com.esc.csdn.fragment.MobileFragment;
import com.esc.csdn.fragment.MySaveFragment;
import com.esc.csdn.fragment.SettingFragment;
import com.esc.csdn.fragment.SoftDevFragment;
import com.esc.csdn.utils.NetUtil;
import com.esc.csdn.utils.ScreenShot;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainFrame extends FragmentActivity implements View.OnClickListener,SensorEventListener,OnTouchListener{

	private String[] mMenu_name;

	private ResideMenu mResideMenu;

	private ResideMenuItem mMenu_cloud;
	private ResideMenuItem mMenu_mobile;
	private ResideMenuItem mMenu_industry;
	private ResideMenuItem mMenu_magzine;
	private ResideMenuItem mMenu_save;
	private ResideMenuItem mMenu_blog;
	private ResideMenuItem mMenu_exit;
	private ResideMenuItem mMenu_settings;
	private SensorManager sensorManager = null;
	private Vibrator vibrator = null;
	private ACache cache = null;
	private String isChecked = "";
	
	private int exitInt = 1;
	private FrameLayout mFrameLayout = null;
	private ImageView mShareBtn=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainframe);
		mShareBtn =  (ImageView) findViewById(R.id.action_bar_id).findViewById(R.id.share_image);
		setupMenu();
		cache = ACache.get(MainFrame.this);
		boolean isConn =  NetUtil.checkNet(MainFrame.this);
		if (isConn) {
			 Toast.makeText(MainFrame.this, "net is open!", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(MainFrame.this, "net is close!", Toast.LENGTH_LONG).show();
		}
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		changeFragment(new SettingFragment());
	}

	private void setupMenu(){
		mResideMenu = new ResideMenu(this);
		mResideMenu.setBackground(R.drawable.menu_background);
		mResideMenu.attachToActivity(this);
		mResideMenu.setMenuListener(mMenuListener);
		mResideMenu.setScaleValue(0.65f);
		mMenu_name = getResources().getStringArray(R.array.menu_array);

		this.mMenu_cloud = new ResideMenuItem(this, R.drawable.bg_cloud, mMenu_name[0]);
		this.mMenu_mobile = new ResideMenuItem(this, R.drawable.bg_mobile, mMenu_name[1]);
		this.mMenu_industry = new ResideMenuItem(this, R.drawable.bg_industry, mMenu_name[2]);
		this.mMenu_magzine = new ResideMenuItem(this, R.drawable.bg_magzine, mMenu_name[3]);
		this.mMenu_blog = new ResideMenuItem(this,R.drawable.bg_blog,mMenu_name[4]);
		this.mMenu_save = new ResideMenuItem(this,R.drawable.bg_save,mMenu_name[5]);
		this.mMenu_settings = new ResideMenuItem(this,R.drawable.bg_settings,mMenu_name[6]);
		this.mMenu_exit = new ResideMenuItem(this,R.drawable.bg_exit,mMenu_name[7]);

		this.mMenu_cloud.setOnClickListener(this);
		this.mMenu_industry.setOnClickListener(this);
		this.mMenu_magzine.setOnClickListener(this);
		this.mMenu_mobile.setOnClickListener(this);
		this.mMenu_blog.setOnClickListener(this);
		this.mMenu_settings.setOnClickListener(this);
		this.mMenu_save.setOnClickListener(this);
		this.mMenu_exit.setOnClickListener(this);

		mResideMenu.addMenuItem(mMenu_cloud, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_mobile, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_industry, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_magzine, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_blog, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_save,ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_settings,ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_exit,ResideMenu.DIRECTION_LEFT);

		ImageView toggleIv = (ImageView) findViewById(R.id.openMenuBtn);

		//open menu
		toggleIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mResideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			}
		});
		//set default title
		this.setActionBarTitle(mMenu_name[0]);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return mResideMenu.dispatchTouchEvent(ev);
	}


	private ResideMenu.OnMenuListener mMenuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			//            Toast.makeText(MainActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void closeMenu() {
			//            Toast.makeText(MainActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
		}
	};


	private void changeFragment(Fragment targetFragment){
		mFrameLayout.setVisibility(View.GONE);
		mResideMenu.clearIgnoredViewList();
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.main_fragment, targetFragment, "fragment")
		.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		.commit();
	}

	/**
	 * set title
	 * @param title title name
	 */
	public void setActionBarTitle(String title){
		TextView titleTv = (TextView) findViewById(R.id.title);
		titleTv.setText(title);
		System.out.println("title:"+titleTv.getText().toString());
	}

	@Override
	public void onClick(View view) {
		if (view == mMenu_cloud){
			setActionBarTitle(mMenu_name[0]);
			changeFragment(new CloudFragment());
		}else if (view == mMenu_industry){
			setActionBarTitle(mMenu_name[2]);
			changeFragment(new IndustryFragment());
		}else if (view == mMenu_magzine){
			setActionBarTitle(mMenu_name[3]);
			changeFragment(new MagzineFragment());

		}else if (view == mMenu_mobile){
			setActionBarTitle(mMenu_name[1]);
			changeFragment(new MobileFragment());
		}else if (view == mMenu_blog){
			setActionBarTitle(mMenu_name[4]);
			changeFragment(new SoftDevFragment());
		}else if (view == mMenu_settings){
			setActionBarTitle(mMenu_name[6]);
			changeFragment(new SettingFragment());
		}else if (view == mMenu_save) {
			setActionBarTitle(mMenu_name[5]);
			changeFragment(new MySaveFragment());
		}else if (view == mMenu_exit) {
		
        	
		}

		mResideMenu.closeMenu();
	}

	public ResideMenu getResideMenu(){
		return mResideMenu;
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	boolean isSavedOne = false;
	@Override
	public void onSensorChanged(SensorEvent event) {
		isChecked = cache.getAsString("checked");
		if (null != isChecked && isChecked.equals("save")) {//sava image
			int sensorType = event.sensor.getType();
			float[]values = event.values;
			if (sensorType == Sensor.TYPE_ACCELEROMETER) {
				if (Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math.abs(values[2]) > 17 ) {
					vibrator.vibrate(500);
					if (!isSavedOne) {
						Bitmap b = ScreenShot.takeScreenShot(MainFrame.this);
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日HH时mm分ss秒");
						File file=new File("/storage/sdcard1/csdn_liuhang");
						if(!file.exists()){
							file.mkdirs();
						}
						ScreenShot.savePic(b,file.getAbsolutePath()+"/"+simpleDateFormat.format(new Date(System.currentTimeMillis()))+".jpg");
						Toast.makeText(MainFrame.this,"图片已保存:"+file.getAbsolutePath()+"/"+simpleDateFormat.format(new Date(System.currentTimeMillis()))+".jpg",700).show();
						isSavedOne = true;
					}

				}
			}
		}
	}
	
	/**
	 * 监听系统的返回健
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (exitInt == 1 && keyCode == KeyEvent.KEYCODE_BACK) {
			mResideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			exitInt = 2;
		}else if (keyCode == KeyEvent.KEYCODE_BACK) {
			mResideMenu.closeMenu();
			exitInt = 1;
		}
		return true; 
    }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
	
}