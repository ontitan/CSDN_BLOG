package com.esc.csdn.dao;

import java.util.ArrayList;
import java.util.List;

import com.esc.csdn.entity.CloudEntity;
import com.esc.csdn.entity.IndustryEntity;
import com.esc.csdn.entity.ProgrammerEntity;
import com.esc.csdn.entity.MobileEntity;
import com.esc.csdn.entity.SoftDevEntity;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import android.content.Context;

public class MobileDao {
	private  List<MobileEntity>list1 = new ArrayList<MobileEntity>();
	private  List<CloudEntity>list2 = new ArrayList<CloudEntity>();
	private  List<IndustryEntity>list3 = new ArrayList<IndustryEntity>();
	private  List<ProgrammerEntity>list4 = new ArrayList<ProgrammerEntity>();
	private  List<SoftDevEntity>list5 = new ArrayList<SoftDevEntity>();
	private List<MobileTitleSave>listMobileTitleSaves = new ArrayList<MobileTitleSave>();
	private Context context;
	DbUtils dbUtils = null;
	public MobileDao(Context context) {
		this.context = context;
		dbUtils = DbUtils.create(context);
	}


	public  long save(MobileEntity mobileEntity) {
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

	public  long save(CloudEntity cloudEntity) {
		long count = -1;
		try {
			dbUtils.save(cloudEntity);
			count = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public List<CloudEntity>getSaveCLoud() {
		try {
			list2 = dbUtils.findAll(CloudEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list2;
	}
	
	public  long save(ProgrammerEntity programmerEntity) {
		long count = -1;
		try {
			dbUtils.save(programmerEntity);
			count = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public List<ProgrammerEntity>getSaveProgrammer() {
		try {
			list4 = dbUtils.findAll(ProgrammerEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list4;
		
	}
	
	public  long save(SoftDevEntity softDevEntity) {
		long count = -1;
		try {
			dbUtils.save(softDevEntity);
			count = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public List<SoftDevEntity>getSaveSoftDev() {
		try {
			list5 = dbUtils.findAll(SoftDevEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list5;
		
	}
	
	public  long save(IndustryEntity industryEntity) {
		long count = -1;
		try {
			dbUtils.save(industryEntity);
			count = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public List<IndustryEntity>getSaveIndustry() {
		try {
			list3 = dbUtils.findAll(IndustryEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list3;
		
	}
	public void saveMobileTitle(MobileTitleSave mobileTitleSave) throws DbException {
		dbUtils.save(mobileTitleSave);
		
	}
	public List<MobileTitleSave>getSavedMobileTitle() throws DbException {
		listMobileTitleSaves = dbUtils.findAll(MobileTitleSave.class);
		return listMobileTitleSaves;
		
	}
}
