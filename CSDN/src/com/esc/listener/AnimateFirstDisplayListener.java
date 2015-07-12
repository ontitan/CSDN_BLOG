package com.esc.listener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class AnimateFirstDisplayListener extends SimpleImageLoadingListener{

	final static  List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		// TODO Auto-generated method stub
		super.onLoadingComplete(imageUri, view, loadedImage);
	}
	
}
