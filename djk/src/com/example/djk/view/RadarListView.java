package com.example.djk.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class RadarListView extends View {
	public RadarListView(Context context) {
		super(context);
		
	}

	private float x;

	private float y;
	
	private float radius;
	@Override
	// 重写该方法,进行绘图
	protected void onDraw(Canvas canvas) {
		x = getCircleXPoint();
		y = getCircleYPoint();
		radius = getCircleRadius();
		super.onDraw(canvas);
		// 把整张画布绘制成白色
		canvas.drawColor(Color.BLACK);
		Paint paint = new Paint();
		// 去锯齿
		paint.setAntiAlias(true);
		int color = Color.argb(120, 0, 255, 0);
		paint.setColor(color);
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(1);
		// 绘制圆形
		canvas.drawCircle(x, y, radius, paint);

		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.LTGRAY);
		canvas.drawCircle(x, y, radius, paint);

		canvas.drawCircle(x, y, radius*0.8f, paint);

		canvas.drawCircle(x, y, radius*0.6f, paint);

		canvas.drawCircle(x, y, radius*0.4f, paint);

		canvas.drawCircle(x, y, radius*0.2f, paint);
		
		canvas.drawLine(x-radius*1.1f, y,x+radius*1.1f,y, paint);
	
		canvas.drawLine(x, y-radius*1.1f,x,y+radius*1.1f, paint);
		
	}
	
	private float getCircleXPoint() {
		return (float) (getLeft() + getRight()) / 2f;
	}

	private float getCircleYPoint() {
		return (float) (getBottom() * 0.5f);
	}

	private float getCircleRadius() {
		if ((getCircleXPoint() - getLeft()) > (getCircleYPoint() - getTop()))
			return (getCircleYPoint() - getTop()) * 0.95f;
		return (getCircleXPoint() - getLeft()) * 0.95f;
	}
}
