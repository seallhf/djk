package com.example.djk.view;

import java.util.Map;

import com.example.djk.activity.RadarListActivity.Location;
import com.example.djk.util.CaculateDistance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

public class DrawView extends View {
	public DrawView(Context context, Map<String, Location> locations,
			Location show, Location sLocation) {
		super(context);
		this.locations = locations;
		this.show = show;
		this.sLocation = sLocation;
	}

	private Location sLocation;

	private Map<String, Location> locations;

	private Location show;

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

		canvas.drawCircle(x, y, radius * 0.8f, paint);

		canvas.drawCircle(x, y, radius * 0.6f, paint);

		canvas.drawCircle(x, y, radius * 0.4f, paint);

		canvas.drawCircle(x, y, radius * 0.2f, paint);

		canvas.drawLine(x - radius * 1.1f, y, x + radius * 1.1f, y, paint);

		canvas.drawLine(x, y - radius * 1.1f, x, y + radius * 1.1f, paint);
		
		canvas.drawText("N", x+radius*0.05f, y-radius*0.95f, paint);		
		
		if (locations != null) {
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.YELLOW);
			for (String shuiku : locations.keySet()) {
				Location l = (Location) locations.get(shuiku);
				Point p = CaculateDistance.caculate(sLocation.getLatitude(),
						sLocation.getLongitude(), l.getLatitude(),
						l.getLongitude(), this);
				if (p != null)
					canvas.drawCircle((float) p.x, (float) p.y, radius * 0.035f,
							paint);
			}
		}
		if (show != null) {
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(radius * 0.03f);
			Point p = CaculateDistance.caculate(sLocation.getLatitude(),
					sLocation.getLongitude(), show.getLatitude(),
					show.getLongitude(), this);
			if (p != null)
				canvas.drawCircle((float) p.x, (float) p.y, radius * 0.045f,
						paint);
		}

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
