package com.esc.csdn.utils;

import android.R.integer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class NetUtil
{

	public static boolean isNetOk=false;
	public static boolean checkNetState(Context context)
	{
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMOBILEConnected(context);
		if (wifiConnected == false && mobileConnected == false)
		{
			return false;
		}
		return true;
	}
	public static boolean isMOBILEConnected(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected())
		{
			return true;
		}
		return false;
	}
	public static boolean isWIFIConnected(Context context)
	{
		// Context.CONNECTIVITY_SERVICE).

		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected())
		{
			return true;
		}
		return false;
	}
	public static boolean netPingState()
	{
		new NetPing().execute(new String[]{"www.baidu.com"});
		
		if(isNetOk)
			return true;
		else 
			return false;
	}
	public static boolean ping(String pingAddress){
		try {
			//ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
			Process mProcess = Runtime.getRuntime().exec("ping -c 2 -w 5 " + pingAddress);
			int status =mProcess.waitFor();
			if(0==status)
			if (status == 0) {
				isNetOk=true;
			} else {
				isNetOk=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return isNetOk;
	}
	public static class NetPing extends AsyncTask<String, integer, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			return ping(params[0]);
		}
	}
}
