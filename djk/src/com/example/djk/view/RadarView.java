package com.example.djk.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

@SuppressLint("ViewConstructor")
public class RadarView extends View{

	public RadarView(Context context,int theta) {
		super(context);
		this.theta=theta;
	}

	private int theta;
	
	private float x;

	private float y;
	
	private float radius;

	int[] colors;

	Shader mSweepGradient;

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		x = getCircleXPoint();
		y = getCircleYPoint();
		int color = Color.argb(120, 0, 255, 0);
		colors = new int[] { color, color,color, color,
				color, color, color,color, color,
				color, color, color,color, color,
				color, color, color,color, color,
				color, color, color,color, color,
				color, color, color,color, color,
				color, color, color,color, color,
				color, color, color,color, color,
				color, color, color,color, color,
				color, color, color,color,color, color};
		if(theta>=0)
			colors[theta]=Color.GREEN;
		if(theta==49)
			colors[0]=Color.GREEN;
		else if(theta+1>=0)
			colors[theta+1]=Color.GREEN;
		mSweepGradient = new SweepGradient(x,
				y, colors, null);
		radius = getCircleRadius();
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		Paint paint = new Paint();
		
		Paint bpaint = new Paint();
		// È¥¾â³Ý
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1);
		paint.setColor(Color.LTGRAY);
		paint.setStyle(Paint.Style.STROKE);
		
		bpaint.setShader(mSweepGradient);
		bpaint.setAntiAlias(true);
		canvas.drawCircle(x, y,
				radius, bpaint);
		
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
		return (float) (getBottom() * 0.45f);
	}

	private float getCircleRadius() {
		if ((getCircleXPoint() - getLeft()) > (getCircleYPoint() - getTop()))
			return (getCircleYPoint() - getTop()) * 0.95f;
		return (getCircleXPoint() - getLeft()) * 0.95f;
	}

}
