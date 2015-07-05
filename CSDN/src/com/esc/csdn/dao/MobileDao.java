package com.esc.csdn.dao;

import java.util.ArrayList;
import java.util.List;

import com.esc.csdn.entity.CloudEntity;
import com.esc.csdn.entity.IndustryEntity;
import com.esc.csdn.entity.MagzineEntity;
import com.esc.csdn.entity.MobileEntity;
import com.esc.csdn.entity.SoftDevEntity;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.UrlQuerySanitizer.ValueSanitizer;
import android.util.Log;

public class MobileDao {
	private  final String TABLE_NAME = "mobile";
	private  ContentValues values = null;
	private  SQLiteDatabase db;
	private  List<MobileEntity>list1 = new ArrayList<MobileEntity>();
	private  List<CloudEntity>list2 = new ArrayList<CloudEntity>();
	private  List<IndustryEntity>list3 = new ArrayList<IndustryEntity>();
	private  List<MagzineEntity>list4 = new ArrayList<MagzineEntity>();
	private  List<SoftDevEntity>list5 = new ArrayList<SoftDevEntity>();
//	private  List<MobileEntity>list6 = new ArrayList<MobileEntity>();
//	private  List<MobileEntity>list7 = new ArrayList<MobileEntity>();
	private List<MobileTitleSave>listMobileTitleSaves = new ArrayList<MobileTitleSave>();
	private Context context;
	DbUtils dbUtils = null;






	public MobileDao(Context context) {
		this.context = context;
		dbUtils = DbUtils.create(context);
	}


	public  long save(MobileEntity mobileEntity) {
//		values = new ContentValues();
//		db = openhelper.getWritableDatabase();
		long count = -1;
		try {
			dbUtils.save(mobileEntity);
			count = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}


	public  List<MobileEntity>getSavedMobile() {
		try {
			list1 = dbUtils.findAll(MobileEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		
		return list1;
	}

	public List<CloudEntity>getSaveCLoud() {
		try {
			list2 = dbUtils.findAll(CloudEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list2;
	}
	
	
	public List<MagzineEntity>getSaveMagzine() {
		try {
			list4 = dbUtils.findAll(MagzineEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list4;
		
	}
	
	
	public List<SoftDevEntity>getSaveSoftDev() {
		try {
			list5 = dbUtils.findAll(SoftDevEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list5;
		
	}
	
	
	public List<IndustryEntity>getSaveIndustry() {
		try {
			list3 = dbUtils.findAll(IndustryEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list3;
		
	}
	public List<MobileTitleSave>getSavedMobileTitle() throws DbException {
		listMobileTitleSaves = dbUtils.findAll(MobileTitleSave.class);
		return listMobileTitleSaves;
		
	}
	
	public void saveMobileTitle(MobileTitleSave mobileTitleSave) throws DbException {
		dbUtils.save(mobileTitleSave);
		
	}
	
	
}
