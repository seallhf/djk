package com.example.djk.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageSwitchView extends ImageView {

	public ImageSwitchView(Context context) {
		this(context, null);
	}

	public ImageSwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private int[] imageIds;
	public int index = 0;
	public int length = 1;

	public void setImageIds(int[] imageId) {
		this.imageIds = imageId;
		if (imageIds != null && imageIds.length > 0) {
			length = imageIds.length;
		}
	}

	public void setImage(int index) {
		this.index = index;
		postInvalidate();
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}

	@Override
	// 重写该方法,进行绘图
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (imageIds != null && imageIds.length > 0) {
			this.setImageResource(imageIds[index]);
		}
	}	
}
