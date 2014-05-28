package com.example.djk.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.djk.MySimpleAdapter;
import com.example.djk.R;
import com.example.djk.util.CaculateDistance;
import com.example.djk.view.DrawView;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;

public class RadarListActivity extends Activity {

	private String result;

	private double weidu;
	private double jingdu;
	private LinearLayout layout;
	private FrameLayout lt;
	private DrawView drawView;
	private Location sLocation;
	private List<Map<String, String>> datalist;
	private static Map<String, Location> shuikuLocations = new HashMap<String, Location>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radar_list);
		initialLocations();
		Intent intent = getIntent();
		result = intent.getStringExtra("result");
		{
			new AlertDialog.Builder(this).setTitle("预警信息")
					.setMessage("在您身边50公里内已经发布了山洪灾害预警！")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("确定", null)
					.setNegativeButton("取消", null).show();
			weidu = intent.getDoubleExtra("Weidu", 0d);
			jingdu = intent.getDoubleExtra("Jingdu", 0d);
			sLocation = new Location(weidu, jingdu);
			// sLocation = new Location(100.3, 100);
			layout = (LinearLayout) RadarListActivity.this
					.findViewById(R.id.radarList_radar_place);
			drawView = new DrawView(RadarListActivity.this,
					getLocations(result), null, sLocation);
			layout.addView(drawView);
			createList();
		}
	}

	private void createList() {
		lt = (FrameLayout) RadarListActivity.this
				.findViewById(R.id.radarList_radar_list);
		lt.removeAllViews();
		ListView dataview = new ListView(RadarListActivity.this);
		datalist = praseDataRadar();
		dataview.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("rawtypes")
			public void onItemClick(AdapterView adapterView, View view,
					int arg2, long arg3) { // TODO Auto-generated method stub
				int selectedPosition = arg2;
				Location show = (Location) shuikuLocations
						.get(((Map<String, String>) datalist
								.get(selectedPosition)).get("id"));
				drawView = new DrawView(RadarListActivity.this,
						getLocations(result), show, sLocation);
				layout.removeAllViews();
				layout.addView(drawView);
			}
		});
		String[] ColumnNames = new String[] { "radar_name", "radar_distance",
				"radar_discription" };
		ListAdapter adapter = new MySimpleAdapter(RadarListActivity.this,
				R.layout.layout_radar_list, datalist, ColumnNames, new int[] {
						R.id.radar_name, R.id.radar_distance,
						R.id.radar_discription });
		dataview.setAdapter(adapter);
		lt.addView(dataview, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

	}

	private void initialLocations() {
		shuikuLocations.put("61940205",
				new Location(CaculateDistance.du2Decimal("32.32.13"),
						CaculateDistance.du2Decimal("111.30.40")));
		shuikuLocations.put("61907600",
				new Location(CaculateDistance.du2Decimal("32.21.38"),
						CaculateDistance.du2Decimal("110.53.18")));
		shuikuLocations.put("62039130",
				new Location(CaculateDistance.du2Decimal("32.44.34"),
						CaculateDistance.du2Decimal("111.22.56")));
		shuikuLocations.put("61907900",
				new Location(CaculateDistance.du2Decimal("32.22.54"),
						CaculateDistance.du2Decimal("111.09.39")));
		shuikuLocations.put("61940210",
				new Location(CaculateDistance.du2Decimal("32.33.17"),
						CaculateDistance.du2Decimal("111.32.16")));
		shuikuLocations.put("61907300",
				new Location(CaculateDistance.du2Decimal("32.48.29"),
						CaculateDistance.du2Decimal("111.11.22")));
		shuikuLocations.put("61907810",
				new Location(CaculateDistance.du2Decimal("32.19.06"),
						CaculateDistance.du2Decimal("110.59.07")));
		shuikuLocations.put("61906860",
				new Location(CaculateDistance.du2Decimal("32.37.59"),
						CaculateDistance.du2Decimal("110.58.44")));
		shuikuLocations.put("61908110",
				new Location(CaculateDistance.du2Decimal("32.28.26"),
						CaculateDistance.du2Decimal("111.26.17")));
		shuikuLocations.put("61907800", new Location());
		shuikuLocations.put("61906870",
				new Location(CaculateDistance.du2Decimal("32.38.40"),
						CaculateDistance.du2Decimal("111.02.42")));
		shuikuLocations.put("61907200",
				new Location(CaculateDistance.du2Decimal("32.47.14"),
						CaculateDistance.du2Decimal("111.16.16")));
		shuikuLocations.put("62010401",
				new Location(CaculateDistance.du2Decimal("32.45.22"),
						CaculateDistance.du2Decimal("111.22.59")));
		shuikuLocations.put("61940060",
				new Location(CaculateDistance.du2Decimal("32.25.24"),
						CaculateDistance.du2Decimal("111.21.37")));
		shuikuLocations.put("61938900",
				new Location(CaculateDistance.du2Decimal("32.26.15"),
						CaculateDistance.du2Decimal("110.54.42")));
		shuikuLocations.put("61939740",
				new Location(CaculateDistance.du2Decimal("32.27.00"),
						CaculateDistance.du2Decimal("111.13.24")));
		shuikuLocations.put("61939860",
				new Location(CaculateDistance.du2Decimal("32.21.32"),
						CaculateDistance.du2Decimal("111.09.39")));
		shuikuLocations.put("61937750",
				new Location(CaculateDistance.du2Decimal("32.51.41"),
						CaculateDistance.du2Decimal("111.10.06")));
		shuikuLocations.put("61937245",
				new Location(CaculateDistance.du2Decimal("32.45.01"),
						CaculateDistance.du2Decimal("111.10.57")));
		shuikuLocations.put("61939720",
				new Location(CaculateDistance.du2Decimal("32.40.02"),
						CaculateDistance.du2Decimal("111.17.38")));
		shuikuLocations.put("61938350",
				new Location(CaculateDistance.du2Decimal("32.45.43"),
						CaculateDistance.du2Decimal("111.15.29")));
		shuikuLocations.put("61939240",
				new Location(CaculateDistance.du2Decimal("32.32.53"),
						CaculateDistance.du2Decimal("110.56.42")));
		shuikuLocations.put("61937700",
				new Location(CaculateDistance.du2Decimal("32.34.15"),
						CaculateDistance.du2Decimal("111.00.48")));
		shuikuLocations.put("61939730",
				new Location(CaculateDistance.du2Decimal("32.35.16"),
						CaculateDistance.du2Decimal("111.15.23")));
		shuikuLocations.put("61940260",
				new Location(CaculateDistance.du2Decimal("32.30.15"),
						CaculateDistance.du2Decimal("111.28.48")));
		shuikuLocations.put("61940100",
				new Location(CaculateDistance.du2Decimal("32.31.57"),
						CaculateDistance.du2Decimal("111.19.19")));
		shuikuLocations.put("61940110",
				new Location(CaculateDistance.du2Decimal("32.38.55"),
						CaculateDistance.du2Decimal("111.23.34")));
		shuikuLocations.put("61938250",
				new Location(CaculateDistance.du2Decimal("32.39.28"),
						CaculateDistance.du2Decimal("111.06.02")));
		shuikuLocations.put("61937760",
				new Location(CaculateDistance.du2Decimal("32.47.10"),
						CaculateDistance.du2Decimal("111.11.07")));
	}

	private List<Map<String, String>> praseDataRadar() {
		// TODO Auto-generated method stub
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		if (result != null) {
			String[] lines = result.split("#");
			int i = 0;
			while (i < lines.length) {
				String line = lines[i++];
				String[] elems = line.split("\\|");
				Map<String, String> v = new HashMap<String, String>();
				if (shuikuLocations.containsKey(elems[0])) {
					String distance = String.valueOf(CaculateDistance
							.getDistance(sLocation.getLatitude(), sLocation
									.getLongitude(), (double) shuikuLocations
									.get(elems[0]).getLatitude(),
									(double) shuikuLocations.get(elems[0])
											.getLongitude()));
					v.put("radar_name", elems[1]);
					v.put("radar_distance", distance + "km");
					v.put("id", elems[0]);
					String des = "";
					if (!elems[2].equals("_"))
						des += "当前水位为(" + elems[2] + "M)";
					if (!elems[3].equals("_"))
						des += elems[4] + "为(" + elems[3] + "mm)";
					v.put("radar_discription", des);
					values.add(v);
				}
			}
		}
		return values;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.radar_list, menu);
		return true;
	}

	private Map<String, Location> getLocations(String result) {
		Map<String, Location> locations = new HashMap<String, Location>();
		String[] lines = result.split("#");
		for (String line : lines) {
			String[] infos = line.split("\\|");
			Location location = shuikuLocations.get(infos[0]);
			locations.put(infos[0], location);
		}
		return locations;
	}

	public class Location {
		private double weidu;
		private double jingdu;

		public Location(double weidu, double jingdu) {
			this.weidu = weidu;
			this.jingdu = jingdu;
		}

		public Location() {
		}

		public double getLatitude() {
			return weidu;
		}

		public void setLatitude(double weidu) {
			this.weidu = weidu;
		}

		public double getLongitude() {
			return jingdu;
		}

		public void setLongitude(double jingdu) {
			this.jingdu = jingdu;
		}

	}

}
