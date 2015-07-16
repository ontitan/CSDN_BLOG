package com.esc.csdn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class NetUtil
{

	public static String mString="";
	public static boolean checkNet(Context context)
	{
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMOBILEConnected(context);
		if (wifiConnected == false && mobileConnected == false)
		{
			return false;
		}
		return true;
	}

	/*public static boolean isWIFIConnected(Context context)
	{
		// Context.CONNECTIVITY_SERVICE).

		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected())
		{
			return true;
		}
		return false;
	}*/
	public static boolean isWIFIConnected(Context context)
	{
		// Context.CONNECTIVITY_SERVICE).

		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected())
		{
			new NetPing().execute();
			if(mString=="success")return true;
		}
		return false;
	}
	public static boolean isMOBILEConnected(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected())
		{
			new NetPing().execute();
			if(mString=="success")return true;
		}
		return false;
	}
	public static String ping(String str){
		String mResult="";
		Process mProcess;
		try {
			
			//ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
			mProcess = Runtime.getRuntime().exec("ping -c 3 -w 100 " + str);
			int status =mProcess.waitFor();
			InputStream input = mProcess.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null){
				buffer.append(line);
				}
			if (status == 0) {
				mResult = "success";
			} else {
				mResult = "faild";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return mResult;
	}
	public static class NetPing extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			mString=ping("www.baidu.com");
			return mString;
		}
		
	}
}
