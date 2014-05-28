package com.example.djk.util;

import android.graphics.Point;
import android.view.View;

public class CaculateDistance {

	private final static double EARTH_RADIUS = 6378.137;

	public static Point caculate(double sWeidu, double sJingdu, double dWeidu,
			double dJingdu, View view) {

		float x = (float) (view.getLeft() + view.getRight()) / 2f;
		float y = (float) (view.getBottom() * 0.5f);
		float radius = getCircleRadius(view, x, y);
		if (getDistance(sWeidu, sJingdu, dWeidu, dJingdu) <= 50) {
			Point p = new Point();
			double pDistance = (getDistance(sWeidu, sJingdu, dWeidu, dJingdu) / 50d)
					* radius;
			int px, py;
			if (dJingdu >= sJingdu)
				px = (int) (x + (pDistance)
						* getCos(sWeidu, sJingdu, dWeidu, dJingdu));
			else
				px = (int) (x - (pDistance)
						* getCos(sWeidu, sJingdu, dWeidu, dJingdu));
			if (dWeidu >= sWeidu)
				py = (int) (y - (pDistance)
						* getSin(sWeidu, sJingdu, dWeidu, dJingdu));
			else
				py = (int) (y + (pDistance)
						* getSin(sWeidu, sJingdu, dWeidu, dJingdu));
			p.set(px, py);
			return p;
		}
		return null;
	}

	public static double du2Decimal(String line) {
		double du = 0d;
		du = (Double.parseDouble(line.split("\\.")[0])
				+ Double.parseDouble(line.split("\\.")[1]) / 60d + Double
				.parseDouble(line.split("\\.")[2]) / 3600d);
		return du;
	}

	private static float getCircleRadius(View view, float x, float y) {
		if ((x - view.getLeft()) > (y - view.getTop()))
			return (y - view.getTop()) * 0.95f;
		return (x - view.getLeft()) * 0.95f;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double getSin(double sWeidu, double sJingdu, double dWeidu,
			double dJingdu) {
		return Math.abs(sWeidu - dWeidu)
				/ Math.pow(
						Math.pow(sWeidu - dWeidu, 2)
								+ Math.pow(sJingdu - dJingdu, 2), 0.5);
	}

	public static double getCos(double sWeidu, double sJingdu, double dWeidu,
			double dJingdu) {
		return Math.abs(sJingdu - dJingdu)
				/ Math.pow(
						Math.pow(sWeidu - dWeidu, 2)
								+ Math.pow(sJingdu - dJingdu, 2), 0.5);
	}

	public static double getDistance(double sWeidu, double sJingdu,
			double dWeidu, double dJingdu) {
		double radLat1 = rad(sWeidu);
		double radLat2 = rad(dWeidu);
		double a = radLat1 - radLat2;
		double b = rad(sJingdu) - rad(dJingdu);

		double s = 2 * Math.asin(Math.pow(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
						* Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2),
				0.5d));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
}
