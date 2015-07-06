package com.esc.csdn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import android.graphics.Shader;

public class FlashText extends TextView{
	
	private LinearGradient mLinearGradient;
	private Matrix mMatrix;
	private Paint mPaint;
	private int mViewWidth=0;
	private int mTranslate=0;
	private boolean mAnimating=true;
	
	public FlashText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public FlashText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FlashText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(mAnimating&&mMatrix!=null){
			mTranslate+=mViewWidth/10;
			if(mTranslate>2*mViewWidth)
				mTranslate=-mViewWidth;
			mMatrix.setTranslate(mTranslate, 0);
			mLinearGradient.setLocalMatrix(mMatrix);
			postInvalidateDelayed(50);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		if(0==mViewWidth){
			mViewWidth=getMeasuredWidth();
			if(mViewWidth>0){
				mPaint=getPaint();
				mLinearGradient=new LinearGradient(-mViewWidth, 0, 0, 0, new int[]{0x66ffffff, 0xffffffff, 0x66ffffff},new float[]{0,0.5f,1}, Shader.TileMode.CLAMP);
				mPaint.setShader(mLinearGradient);
				mMatrix=new Matrix();
			}
		}
	}
	
}
