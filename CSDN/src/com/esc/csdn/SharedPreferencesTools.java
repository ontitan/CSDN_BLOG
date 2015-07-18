package com.esc.csdn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesTools {

	private static String mFileName="PAGE_FILE";
	
	@SuppressLint("CommitPrefEdits")
	public static void setParam(Context mContext,String mKey,Object mObject){
		String mtype=mObject.getClass().getSimpleName();
		SharedPreferences mPreferences=mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor mEditor=mPreferences.edit();
		
		if(mtype.equals("String")){
			mEditor.putString(mKey, (String)mObject);
		}else if(mtype.equals("Integer")){
			mEditor.putInt(mKey, (Integer)mObject);
		}else if(mtype.equals("Boolean")){
			mEditor.putBoolean(mKey, (Boolean)mObject);
		}else if(mtype.equals("Float")){  
			mEditor.putFloat(mKey, (Float)mObject);  
        }  
        else if(mtype.equals("Long")){  
        	mEditor.putLong(mKey, (Long)mObject);  
        }  
        mEditor.commit();  
	}
	public static Object getParam(Context mContext , String mKey, Object defaultObject){  
        String mType = defaultObject.getClass().getSimpleName();  
        SharedPreferences mPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);  
          
        if(mType.equals("String")){  
            return mPreferences.getString(mKey, (String)defaultObject);  
        }  
        else if(mType.equals("Integer")){  
            return mPreferences.getInt(mKey, (Integer)defaultObject);  
        }  
        else if(mType.equals("Boolean")){  
            return mPreferences.getBoolean(mKey, (Boolean)defaultObject);  
        }  
        else if(mType.equals("Float")){  
            return mPreferences.getFloat(mKey, (Float)defaultObject);  
        }  
        else if(mType.equals("Long")){  
            return mPreferences.getLong(mKey, (Long)defaultObject);  
        }  
        return null;  
    }  
	
}
