package com.esc.listener;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class AnimateFirstDisplayListener extends SimpleImageLoadingListener{

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		// TODO Auto-generated method stub
		super.onLoadingComplete(imageUri, view, loadedImage);
	}
	
}
