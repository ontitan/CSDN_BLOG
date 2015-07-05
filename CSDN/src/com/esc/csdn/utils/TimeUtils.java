package com.esc.csdn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
	String currentTime = "";
	
	
	public static String getCurrentTime() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return dateFormat.format(date);
	}
	
	
	
	
}
