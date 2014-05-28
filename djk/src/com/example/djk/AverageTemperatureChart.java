/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.djk;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.achartengine.ChartFactory;
import org.achartengine.chart.*;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.djk.util.ShuikuLevel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

/**
 * Average temperature demo chart.
 */
public class AverageTemperatureChart extends AbstractDemoChart {
	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Average temperature";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "The average temperature in 4 Greek islands (line chart)";
	}

	private String ytitle;
	private String xtitle;
	public String title;
	private String id;

	private XYMultipleSeriesRenderer getRenderer(int[] colors,
			PointStyle[] styles, double ymax, double ymin, int num) {
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);
			XYSeriesRenderer r = (XYSeriesRenderer) renderer
					.getSeriesRendererAt(i);
			r.setLineWidth(3f);
		}
		setChartSettings(renderer, title, xtitle, ytitle, 0, 12, ymin, ymax,
				Color.GRAY, Color.BLACK);
		renderer.setXAxisMin(0);
		renderer.setXLabels(0);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setAxesColor(Color.BLACK);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setApplyBackgroundColor(true);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setMarginsColor(Color.WHITE);
		renderer.setBackgroundColor(Color.argb(127, 253, 255, 175));
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setZoomButtonsVisible(false);
		for (int i = 0; i < 24; i += 7) {
			// if (i > 0)
			renderer.addXTextLabel(i, day[i]);
		}
		SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(12);
		r.setChartValuesSpacing(6);
		r.setGradientEnabled(true);
		r.setGradientStart(0, Color.BLUE);
		r.setGradientStop(1000, Color.BLUE);
		r = renderer.getSeriesRendererAt(1);
		r.setDisplayChartValues(true);
		r.setChartValuesTextAlign(Align.LEFT);
		r.setChartValuesTextSize(12);
		r.setChartValuesSpacing(6);
		r.setGradientEnabled(true);
		r.setGradientStart(0, Color.RED);
		r.setGradientStop(1000, Color.RED);
		renderer.setPanLimits(new double[] { 0, 30, 0, ymax });
		renderer.setZoomLimits(new double[] { 0, 30, 0, ymax });
		return renderer;
	}

	private XYMultipleSeriesRenderer getRangeRenderer(int[] colors,
			PointStyle[] styles, double ymax, double ymin, int num) {
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
		setChartSettings(renderer, title, xtitle, ytitle, 0, 12.5, ymin, ymax,
				Color.WHITE, Color.BLACK);
		renderer.setXAxisMin(0);
		renderer.setBarSpacing(1);
		renderer.setXLabels(0);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setAxesColor(Color.BLACK);
		renderer.setXLabelsColor(Color.BLACK);
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setApplyBackgroundColor(true);
		renderer.setMarginsColor(Color.WHITE);
		renderer.setBackgroundColor(Color.argb(127, 253, 255, 175));
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setZoomButtonsVisible(false);
		renderer.setPanLimits(new double[] { 0, 30, 0, ymax });
		renderer.setZoomLimits(new double[] { 0, 30, 0, ymax });
		for (int i = 0; i < 24; i += 4) {
			if (num != 0) {
				if (i != 0)
					renderer.addXTextLabel(
							i - 1,
							String.valueOf(counts[i - 1] * Math.pow(2, num - 1)
									* 3 + "h"));
				else
					renderer.addXTextLabel(i, String.valueOf(counts[i] + "h"));
			} else {
				if (i != 0)
					renderer.addXTextLabel(i - 1,
							String.valueOf(counts[i - 1] + "h"));
				else
					renderer.addXTextLabel(i, String.valueOf(counts[i] + "h"));
			}
		}
		SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(12);
		r.setChartValuesSpacing(3);
		r.setGradientEnabled(true);
		r.setGradientStart(0, Color.BLUE);
		r.setGradientStop(1000, Color.BLUE);
		// r.setDisplayBoundingPoints(false);
		return renderer;
	}

	public Intent execute(Context context) {
		return null;
	}

	private double[] sum1;
	private double[] sum3;
	private double[] sum6;
	private double[] sum12;
	private double[] sum24;
	private double[] water;
	private String[] day;
	private double[] counts;
	private double[] xunxian;
	private String ShuiKu;

	private void praseData(String data) {
		Pattern p = Pattern.compile("<elem>.+?</elem>");
		Matcher m = p.matcher(data);
		sum1 = new double[24];
		sum3 = new double[24];
		sum6 = new double[24];
		sum12 = new double[24];
		sum24 = new double[24];
		water = new double[24];
		day = new String[24];
		counts = new double[24];
		xunxian = new double[24];
		while (m.find()) {
			String line = m.group().replaceAll("<elem>||</elem>", "");
			line = line.replaceAll("</.+?>", "#").replaceAll("<.+?>", "");
			String[] lines = line.split("#");
			int count = Integer.parseInt(lines[0]);
			counts[count] = Double.parseDouble(lines[0]);
			day[count] = lines[1].split(" ")[0];
			sum1[count] = Double.parseDouble(lines[2]);
			sum3[count] = Double.parseDouble(lines[3]);
			sum6[count] = Double.parseDouble(lines[4]);
			sum12[count] = Double.parseDouble(lines[5]);
			sum24[count] = Double.parseDouble(lines[6]);
			water[count] = Double.parseDouble(lines[7]);
		}
		p = Pattern.compile("<name>.+?</name>");
		m = p.matcher(data);
		if (m.find()) {
			ShuiKu = m.group().replaceAll("<.+?>", "");
		}
	}

	public XYMultipleSeriesDataset getDataSet(String result, int num) {
		RangeCategorySeries series = new RangeCategorySeries(ShuiKu);
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		String[] titles = new String[] { ShuiKu };
		if (num == 5)
			titles = new String[] {
					ShuiKu,
					"Ñ´ÏÞË®Î»"
							+ Double.parseDouble(ShuikuLevel.xunxianlevel
									.get(id))+"m" };
		List<double[]> x = new ArrayList<double[]>();
		List<double[]> values = new ArrayList<double[]>();
		xtitle = "";
		switch (num) {
		case 0:
			int length = sum1.length;
			for (int k = 0; k < length; k++) {
				series.add(0, sum1[k]);
			}
			dataset.addSeries(series.toXYSeries());
			ytitle = "(mm)";
			return dataset;
		case 1:
			length = sum3.length;
			for (int k = 0; k < length; k++) {
				series.add(0, sum3[k]);
			}
			dataset.addSeries(series.toXYSeries());
			ytitle = "(mm)";
			return dataset;
		case 2:
			length = sum6.length;
			for (int k = 0; k < length; k++) {
				series.add(0, sum6[k]);
			}
			dataset.addSeries(series.toXYSeries());
			ytitle = "(mm)";
			return dataset;
		case 3:
			length = sum12.length;
			for (int k = 0; k < length; k++) {
				series.add(0, sum12[k]);
			}
			dataset.addSeries(series.toXYSeries());
			ytitle = "(mm)";
			return dataset;
		case 4:
			length = sum24.length;
			for (int k = 0; k < length; k++) {
				series.add(0, sum24[k]);
			}
			dataset.addSeries(series.toXYSeries());
			ytitle = "(mm)";
			return dataset;
		case 5:
			values.add(water);
			values.add(xunxian);
			x.add(counts);
			x.add(counts);
			ytitle = "(m)";
			break;
		}
		dataset = buildDataset(titles, x, values);
		return dataset;
	}

	public View intent;

	public double getYmax(int num) {
		double max = 0.0;
		switch (num) {
		case 0:
			for (int i = 0; i < counts.length; i++) {
				if (sum1[i] > max)
					max = sum1[i];
			}
			break;
		case 1:
			for (int i = 0; i < counts.length; i++) {
				if (sum3[i] > max)
					max = sum3[i];
			}
			break;
		case 2:
			for (int i = 0; i < counts.length; i++) {
				if (sum6[i] > max)
					max = sum6[i];
			}
			break;
		case 3:
			for (int i = 0; i < counts.length; i++) {
				if (sum12[i] > max)
					max = sum12[i];
			}
			break;
		case 4:
			for (int i = 0; i < counts.length; i++) {
				if (sum24[i] > max)
					max = sum24[i];
			}
			break;
		case 5:
			for (int i = 0; i < counts.length; i++) {
				if (water[i] > max)
					max = water[i];
			}
			break;
		}
		if (max == 0.0)
			return 20;

		return max + 1;
	}

	public double getYmin(int num) {
		double max = 0.0;
		switch (num) {
		case 0:
			max = sum1[0];
			for (int i = 0; i < counts.length; i++) {
				if (sum1[i] < max)
					max = sum1[i];
			}
			break;
		case 1:
			max = sum3[0];
			for (int i = 0; i < counts.length; i++) {
				if (sum3[i] < max)
					max = sum3[i];
			}
			break;
		case 2:
			max = sum6[0];
			for (int i = 0; i < counts.length; i++) {
				if (sum6[i] < max)
					max = sum6[i];
			}
			break;
		case 3:
			max = sum12[0];
			for (int i = 0; i < counts.length; i++) {
				if (sum12[i] < max)
					max = sum12[i];
			}
			break;
		case 4:
			max = sum24[0];
			for (int i = 0; i < counts.length; i++) {
				if (sum24[i] < max)
					max = sum24[i];
			}
			break;
		case 5:
			max = water[0];
			for (int i = 0; i < counts.length; i++) {
				if (water[i] < max)
					max = water[i];
			}
			break;
		}
		return max;
	}

	public View getView(Context context, String data, int num,String location) {
		id = location;
		if (num == 5) {
			praseWaterData(data,location);
			int[] colors = new int[] { Color.BLUE, Color.RED };
			PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
					PointStyle.POINT };
			intent = ChartFactory
					.getLineChartView(
							context,
							getDataSet(data, num),
							getRenderer(colors, styles, getYmax(num),
									getYmin(num), num));

		} else {
			praseData(data);
			int[] colors = new int[] { Color.BLUE };
			PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE };
			intent = ChartFactory.getRangeBarChartView(
					context,
					getDataSet(data, num),
					getRangeRenderer(colors, styles, getYmax(num),
							getYmin(num), num), Type.DEFAULT);
		}
		return intent;
	}

	private void praseWaterData(String data,String location) {
		// TODO Auto-generated method stub
		
		Pattern p = Pattern.compile("<elem>.+?</elem>");
		Matcher m = p.matcher(data);
		sum1 = new double[24];
		sum3 = new double[24];
		sum6 = new double[24];
		sum12 = new double[24];
		sum24 = new double[24];
		water = new double[24];
		day = new String[24];
		counts = new double[24];
		xunxian = new double[24];
		while (m.find()) {
			String line = m.group().replaceAll("<elem>||</elem>", "");
			line = line.replaceAll("</.+?>", "#").replaceAll("<.+?>", "");
			String[] lines = line.split("#");
			int count = Integer.parseInt(lines[0]);
			counts[count] = Double.parseDouble(lines[0]);
			day[count] = lines[1].split(" ")[0];
			sum1[count] = Double.parseDouble(lines[2]);
			sum3[count] = Double.parseDouble(lines[3]);
			sum6[count] = Double.parseDouble(lines[4]);
			sum12[count] = Double.parseDouble(lines[5]);
			sum24[count] = Double.parseDouble(lines[6]);
			water[count] = Double.parseDouble(lines[7]);
			if(ShuikuLevel.xunxianlevel.containsKey(id))
				xunxian[count] = Double.parseDouble(ShuikuLevel.xunxianlevel
					.get(id));
			else
				xunxian[count]=0d;
		}
		p = Pattern.compile("<name>.+?</name>");
		m = p.matcher(data);
		if (m.find()) {
			ShuiKu = m.group().replaceAll("<.+?>", "");
		}
	}
}
