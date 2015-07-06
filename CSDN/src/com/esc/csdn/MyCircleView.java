package com.esc.csdn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyCircleView extends View {
	int degree = 15;//每次增加的角度
	int i = 0;  //定义小圆的角度
	float rX; //大圆圆心的x坐标
	float rY; //大圆圆心的y坐标
	float radius;  //大圆的半径
	float smallRadius; //小圆的半径
	int smallColor; //设置小圆的颜色
	Paint paint = new Paint();
	public MyCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		smallColor = Color.parseColor("#00ccff");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		rX = getWidth()/2;
		rY = getHeight()/2;
		radius = getWidth()/8;
		smallRadius = rX/25;


			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.rgb(255,157,157));
			paint.setStrokeWidth(2);

			canvas.drawCircle(rX, rY, radius, paint);//画大圆
			if (i != 360) {
			paint.setColor(smallColor);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(rX + (float)Math.cos(Math.toRadians(90-i))*radius,rY - (float)Math.sin(Math.toRadians(90-i))*radius, smallRadius, paint);
			i = i + 15;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			i = 0;
		}

		invalidate();
	}

}
