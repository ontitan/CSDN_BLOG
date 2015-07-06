package com.esc.csdn;

import org.netshull.csdn.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CirclePro extends View {


	private Paint paint;
	private int circleBack;//圆的背景色
	private int mschedual = 0;//用于控制动态变化
	float circleHalf; //圆的半径
	String percent = "";//绘制百分比的字符串
	private ClearData clearData;
	private boolean  isFirstReturn = false;
	public interface ClearData {
		public void hide();
	}
	
	
	
	public void setClearData(ClearData clearData) {
		this.clearData = clearData;
	}

	@SuppressLint("Recycle")
	public CirclePro(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
		TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.myCircleImage, defStyleAttr,0);
		@SuppressWarnings("unused")
		int leng = array.length();
		//获取自定义的属性，这里注意是R.styleable.myCircleImage_circlecolor而不是R.attr.circlecolor
		circleBack = array.getColor(R.styleable.myCircleImage_circlecolor,Color.GREEN);
		circleHalf = array.getDimension(R.styleable.myCircleImage_half,200.f);
		System.out.println(circleBack);
		
	}

	/**
	 * 这个构造参数，当在布局文件中引用该view的时候，必须重写该构造函数
	 * @param context
	 * @param attrs
	 */
	public CirclePro(Context context, AttributeSet attrs) {
		this(context, attrs, 0);//调用自己的构造函数
		
	}
	
	/**
	 * 根据文本的
	 * @param text
	 * @param textSize
	 * @return
	 */
	public float getTextWidth(String text,float textSize) {

		TextPaint textPaint = new TextPaint();
		textPaint.setTextSize(textSize);
		return textPaint.measureText(text);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		float height = getMeasuredHeight();
			
//			getHeight();
		float width = getMeasuredWidth();
//			getWidth();
//		float circleHalf = (float) (width*0.7/2);

		paint.setColor(circleBack);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(width/2,2*height/3,circleHalf, paint);//画实心圆
		
		if (mschedual <= 100) {//，如果当前进度小于100，画实心矩形
			paint.setColor(Color.parseColor("#ffffff"));
			canvas.drawRect(width/2-circleHalf,height/2-circleHalf,width/2+circleHalf,height/2+circleHalf - mschedual*circleHalf/50, paint);
		}
		
		//画当前进度的字符串
		paint.setColor(Color.BLACK);
		paint.setTextSize(30f);
		percent = mschedual+" %";
		canvas.drawText(percent, width/2-getTextWidth(percent,30)/2,height/2+paint.getTextSize()*3/8, paint);//字体的高度=paint.getTextSize()*3/4
		
		//画空心圆
		paint.setColor(circleBack);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(width/2,2*height/3,circleHalf, paint);

		if (mschedual < 100) {//更改当前进度值，并重绘
			mschedual++;
			invalidate();
		}else{
			if (!isFirstReturn) {
				clearData.hide();
				isFirstReturn = true;
			}
			
		}
	}
	
	
//	public void setSchedual(int progress) {
//		mschedual = progress;
//		invalidate();
//	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = 0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			Log.d("test",x+"---ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			x = event.getX();
			Log.d("test",x+"---ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			x = event.getX();
			Log.d("test",x+"---ACTION_UP");
			break;
		default:
			break;
		}
		return true;
	}
}
