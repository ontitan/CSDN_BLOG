package com.esc.csdn;

import java.util.ArrayList;
import java.util.HashMap;

import org.netshull.csdn.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.esc.csdn.fragment.CloudFragment;
import com.esc.csdn.fragment.IndustryFragment;
import com.esc.csdn.fragment.ProgrammerFragment;
import com.esc.csdn.fragment.MobileFragment;
import com.esc.csdn.fragment.MySaveFragment;
import com.esc.csdn.fragment.SettingFragment;
import com.esc.csdn.fragment.SoftDevFragment;
import com.esc.csdn.utils.NetUtil;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainFrame extends FragmentActivity implements View.OnClickListener,OnTouchListener{

	private String[] mMenu_name;

	private ResideMenu mResideMenu;

	private ResideMenuItem mMenu_cloud;
	private ResideMenuItem mMenu_mobile;
	private ResideMenuItem mMenu_industry;
	private ResideMenuItem mMenu_programmer;
	private ResideMenuItem mMenu_save;
	private ResideMenuItem mMenu_sw_development;
	private ResideMenuItem mMenu_exit;
	private ResideMenuItem mMenu_settings;
	private ACache cache = null;
	
	private ImageView mShareBtn=null;
	private ImageView mOpenMenuBtn=null;
	private AlertDialog mExitDialog;
	private AlertDialog mShareDialog;
	private GridView mShareGridView;
	private View mView;

	private final String[]mShareText={"QQ","QQ空间","微信","微信好友"};
	private final int[]mShareImg={R.drawable.qq,R.drawable.qzone,R.drawable.wechat,R.drawable.wechat2};

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainframe);
		setupMenu();
		setupShareMenu();
		cache = ACache.get(MainFrame.this);
		boolean isConn =  NetUtil.checkNet(MainFrame.this);
		if (isConn) {
			 //Toast.makeText(MainFrame.this, "net is open!", Toast.LENGTH_LONG).show();
			 
		}else{
			//Toast.makeText(MainFrame.this, "net is close!", Toast.LENGTH_LONG).show();
		}
		changeFragment(new CloudFragment());
	}
	private void setupShareMenu(){
		mView=View.inflate(this, R.layout.share_content, null);
		mShareDialog=new AlertDialog.Builder(this).create();
		mShareDialog.setView(mView,0,0,0,0);
		mShareDialog.setCanceledOnTouchOutside(true);
		mShareDialog.setInverseBackgroundForced(true);
		Window mWindow=mShareDialog.getWindow();
		mWindow.setGravity(Gravity.BOTTOM);
		mWindow.setWindowAnimations(R.style.window_style);

		mShareGridView=(GridView)mView.findViewById(R.id.content_gridview);
		mShareGridView.setAdapter(getAdapter(mShareImg, mShareText));
		mShareBtn=(ImageView)findViewById(R.id.share_image);
		mShareBtn.setOnClickListener(this);
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
		this.mMenu_programmer = new ResideMenuItem(this, R.drawable.bg_programmer, mMenu_name[3]);
		this.mMenu_sw_development = new ResideMenuItem(this,R.drawable.bg_sw_development,mMenu_name[4]);
		this.mMenu_save = new ResideMenuItem(this,R.drawable.bg_save,mMenu_name[5]);
		this.mMenu_settings = new ResideMenuItem(this,R.drawable.bg_settings,mMenu_name[6]);
		this.mMenu_exit = new ResideMenuItem(this,R.drawable.bg_exit,mMenu_name[7]);

		this.mMenu_cloud.setOnClickListener(this);
		this.mMenu_industry.setOnClickListener(this);
		this.mMenu_programmer.setOnClickListener(this);
		this.mMenu_mobile.setOnClickListener(this);
		this.mMenu_sw_development.setOnClickListener(this);
		this.mMenu_settings.setOnClickListener(this);
		this.mMenu_save.setOnClickListener(this);
		this.mMenu_exit.setOnClickListener(this);

		mResideMenu.addMenuItem(mMenu_cloud, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_mobile, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_industry, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_programmer, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_sw_development, ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_save,ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_settings,ResideMenu.DIRECTION_LEFT);
		mResideMenu.addMenuItem(mMenu_exit,ResideMenu.DIRECTION_LEFT);
		mOpenMenuBtn = (ImageView) findViewById(R.id.openMenuBtn);
		mOpenMenuBtn.setOnClickListener(this);
		
		this.setActionBarTitle(mMenu_name[0]);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return mResideMenu.dispatchTouchEvent(ev);
	}


	private ResideMenu.OnMenuListener mMenuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
		}

		@Override
		public void closeMenu() {
		}
	};


	private void changeFragment(Fragment targetFragment){
		mResideMenu.clearIgnoredViewList();
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.main_fragment, targetFragment)
		.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		.commit();
	}

	
	public void setActionBarTitle(String title){
		TextView titleTv = (TextView) findViewById(R.id.title);
		titleTv.setText(title);
	}

	@Override
	public void onClick(View view) {
		
		if (view == mMenu_cloud){
			setActionBarTitle(mMenu_name[0]);
			changeFragment(new CloudFragment());
		}else if (view == mMenu_industry){
			setActionBarTitle(mMenu_name[2]);
			changeFragment(new IndustryFragment());
		}else if (view == mMenu_programmer){
			setActionBarTitle(mMenu_name[3]);
			changeFragment(new ProgrammerFragment());

		}else if (view == mMenu_mobile){
			setActionBarTitle(mMenu_name[1]);
			changeFragment(new MobileFragment());
		}else if (view == mMenu_sw_development){
			setActionBarTitle(mMenu_name[4]);
			changeFragment(new SoftDevFragment());
		}else if (view == mMenu_settings){
			setActionBarTitle(mMenu_name[6]);
			changeFragment(new SettingFragment());
		}else if (view == mMenu_save) {
			setActionBarTitle(mMenu_name[5]);
			changeFragment(new MySaveFragment());
		}else if (view == mMenu_exit) {
			showExitDialog();
		}else{ 
			switch (view.getId()) {
			case R.id.canelBtn:
				mExitDialog.cancel();
				break;
			case R.id.okBtn:
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
				break;
			case R.id.openMenuBtn:
				mResideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
				break;
			case R.id.share_image:
				showShareDialog();
				break;
			default:
				break;
			}
		}
		if(mResideMenu.isOpened()&&view.getId()!=R.id.openMenuBtn)
			mResideMenu.closeMenu();
	}
	private void showShareDialog(){
		if(null==mShareDialog){
			mShareDialog=new AlertDialog.Builder(this).setView(mView).show();
		}else{
			mShareDialog.show();
		}
	}
	private SimpleAdapter getAdapter(int[] mShareImg, String[] mShareText) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>>mData=new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<mShareText.length;i++){
			HashMap<String, Object>mMap=new HashMap<String, Object>();
			mMap.put("mShareImg", mShareImg[i]);
			mMap.put("mShareText", mShareText[i]);
			mData.add(mMap);
		}
		SimpleAdapter mSimpleAdapter=new SimpleAdapter(this, mData, R.layout.share_content_item, new String[]{"mShareImg","mShareText"}, new int[]{R.id.content_share_img,R.id.content_share_text});
		return mSimpleAdapter;
	}


	private OnItemClickListener mGridViewItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
		}
	};
	private void showExitDialog() {
		// TODO Auto-generated method stub
		mExitDialog=new AlertDialog.Builder(this).create();
		mExitDialog.show();
		Window mWindow=mExitDialog.getWindow();
		mWindow.setContentView(R.layout.tips_window);
		TextView canelTextView=(TextView)mWindow.findViewById(R.id.canelBtn);
		TextView okTextView=(TextView)mWindow.findViewById(R.id.okBtn);
		canelTextView.setOnClickListener(this);
		okTextView.setOnClickListener(this);
		
	}
	public ResideMenu getResideMenu(){
		return mResideMenu;
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	

	boolean isSavedOne = false;
	
	
	/**
	 * 监听系统的返回健
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(mResideMenu.isOpened()){
				mResideMenu.closeMenu();
				showExitDialog();
			}else{
				showExitDialog();
			}
			
		}
		return true;
    }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return true;
	}
}