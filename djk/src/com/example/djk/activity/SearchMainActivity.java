package com.example.djk.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.djk.MySimpleAdapter;
import com.example.djk.R;
import com.example.djk.util.ShuikuLevel;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class SearchMainActivity extends Activity {

	private LinearLayout layout;
	private FrameLayout lt;
	private Button btnSearch;
	private Spinner snrSearch;
	private Spinner snrLocation;
	private Spinner snrStartHour;
	private Spinner snrEndHour;
	private EditText startDate;
	private EditText endDate;
	private String startTime;
	private String endTime;
	private String startHour;
	private String endHour;
	private String op;
	private String shuikuId;
	private String shuikuName;
	public MyHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_main);
		lt = (FrameLayout) this.findViewById(R.id.searchList);
		snrSearch = (Spinner) this.findViewById(R.id.ItemSpinner);
		snrLocation = (Spinner) this.findViewById(R.id.ShuikuSpinner);
		snrStartHour = (Spinner) this.findViewById(R.id.StartTimeSpinner);
		snrEndHour = (Spinner) this.findViewById(R.id.EndTimeSpinner);
		btnSearch = (Button) this.findViewById(R.id.SearchButton);
		startDate = (EditText) this.findViewById(R.id.StartEditText);
		endDate = (EditText) this.findViewById(R.id.EndEditText);
		handler = new MyHandler();
		btnSearch.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shuikuName = snrLocation.getSelectedItem().toString();
				TextView txtView = new TextView(SearchMainActivity.this);
				txtView.setText("正在查询，请等待……");
				lt.removeAllViews();
				lt.addView(txtView);
				if (ShuikuLevel.ShuikuID.containsKey(shuikuName))
					shuikuId = ShuikuLevel.ShuikuID.get(shuikuName);
				int _op = snrSearch.getSelectedItemPosition();
				op = String.valueOf(_op);
				startHour = snrStartHour.getSelectedItem().toString();
				endHour = snrEndHour.getSelectedItem().toString();
				switch (_op) {
				case 0: {
					try {
						Date start;
						Date end;
						if (!endDate.getText().toString().equals("")
								|| !startDate.getText().toString().equals("")) {
							start = new SimpleDateFormat("yyyy-MM-dd")
									.parse(startDate.getText().toString());
							end = new SimpleDateFormat("yyyy-MM-dd")
									.parse(endDate.getText().toString());
							if (start.after(end))
								Toast.makeText(SearchMainActivity.this,
										"起点时间在终点时间之后，请正确输入！", 2000).show();
							else if (start.equals(end)) {
								if (Integer.parseInt(startHour) >= Integer
										.parseInt(endHour))
									Toast.makeText(SearchMainActivity.this,
											"起点时间在终点时间之后，请正确输入！", 2000).show();
							} else {
								Calendar c = Calendar.getInstance();
								c.setTime(start);
								c.set(Calendar.HOUR_OF_DAY,
										Integer.parseInt(startHour));
								start = c.getTime();
								c.setTime(end);
								c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endHour));
								end = c.getTime();
								DetailDataCacher dc = new DetailDataCacher(
										"getSearch", shuikuId, op,
										new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss")
												.format(start),
										new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss")
												.format(end));
								new Thread(dc).start();
							}
						}
						break;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				case 1: {
					try {
						Date start;
						Date end;
						if (!endDate.getText().toString().equals("")
								|| !startDate.getText().toString().equals("")) {
							start = new SimpleDateFormat("yyyy-MM-dd")
									.parse(startDate.getText().toString());
							end = new SimpleDateFormat("yyyy-MM-dd")
									.parse(endDate.getText().toString());
							if (start.after(end))
								Toast.makeText(SearchMainActivity.this,
										"起点时间在终点时间之后，请正确输入！", 2000).show();
							else if (start.equals(end)) {
								if (Integer.parseInt(startHour) >= Integer
										.parseInt(endHour))
									Toast.makeText(SearchMainActivity.this,
											"起点时间在终点时间之后，请正确输入！", 2000).show();
							} else {
								Calendar c = Calendar.getInstance();
								c.setTime(start);
								c.set(Calendar.HOUR_OF_DAY,
										Integer.parseInt(startHour));
								start = c.getTime();
								c.setTime(end);
								c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endHour));
								end = c.getTime();
								DetailDataCacher dc = new DetailDataCacher(
										"getSearch", shuikuId, op,
										new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss")
												.format(start),
										new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss")
												.format(end));
								new Thread(dc).start();
							}
						}
						break;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				case 2:
					if (!endDate.getText().toString().equals("")
							|| !startDate.getText().toString().equals("")) {
						try {
							Date start = new SimpleDateFormat("yyyy-MM-dd")
									.parse(startDate.getText().toString());
							Calendar c = Calendar.getInstance();
							c.setTime(start);
							c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHour));
							start = c.getTime();
							DetailDataCacher dc = new DetailDataCacher(
									"getSearch", shuikuId, op,
									new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
											.format(start),
									"2012-12-1 00:00:00");
							new Thread(dc).start();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				}
			}
		});
		startDate = (EditText) this.findViewById(R.id.StartEditText);
		endDate = (EditText) this.findViewById(R.id.EndEditText);
		lt = (FrameLayout) this.findViewById(R.id.searchList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_main, menu);
		return true;
	}

	List<Map<String, String>> datalist = new ArrayList<Map<String, String>>();

	private void createList() {
		lt.removeAllViews();
		ListView dataview = new ListView(this);
		datalist = praseDataRadar();
		String[] ColumnNames = new String[] { "radar_name", "radar_distance",
				"radar_discription" };
		ListAdapter adapter = new MySimpleAdapter(this,
				R.layout.layout_radar_list, datalist, ColumnNames, new int[] {
						R.id.radar_name, R.id.radar_distance,
						R.id.radar_discription });
		dataview.setAdapter(adapter);
		lt.addView(dataview, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

	}

	private List<Map<String, String>> praseDataRadar() {
		// TODO Auto-generated method stub
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		if (result != null&&!result.equals("anyType{}")) {
			String[] lines = result.split("#");
			int i = 0;
			while (i < lines.length) {
				String line = lines[i++];
				String[] elems = line.split("\\|");
				Map<String, String> v = new HashMap<String, String>();
				v.put("radar_name", elems[0]);
				v.put("radar_distance", elems[1] + "时");
				if (op.equals("2")) {
					if (elems.length <= 2) {
						String des = "该时刻数据缺失！";
						v.put("radar_discription", des);
					} else {
						String des = "当前水位为(" + elems[2] + "M)";
						v.put("radar_discription", des);
					}

				} else {
					if (elems.length <= 2) {
						String des = "数据缺失";
						v.put("radar_discription", des);
					} else {
						String des = "降雨量为(" + elems[2] + "mm)";
						v.put("radar_discription", des);
					}
					
				}
				values.add(v);
			}
		}

		return values;
	}

	private String result;

	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			// 此处可以更新UI
			Bundle b = msg.getData();
			String detaildata = b.getString("result");
			result = detaildata;
			createList();
		}
	}

	class DetailDataCacher implements Runnable {

		final String serviceUrl = "http://61.184.84.212:10000/webService.asmx";
		private String method;
		private String location;
		private String start;
		private String end;
		private String op;

		public DetailDataCacher(String method, String location, String op,
				String start, String end) {
			this.method = method;
			this.location = location;
			this.start = start;
			this.end = end;
			this.op = op;
		}

		public void run() {
			{
				String result = "";
				SoapObject request = new SoapObject("http://tempuri.org/",
						method);
				if (!location.equals("")) {
					request.addProperty("location", location);
				}
				request.addProperty("op", op);
				request.addProperty("_start", start);
				request.addProperty("_end", end);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(serviceUrl);
				envelope.bodyOut = request;
				try {
					ht.call("http://tempuri.org/" + method, envelope);
					if (envelope.bodyIn != null) {
						SoapObject soapObject = (SoapObject) envelope.bodyIn;
						result = soapObject.getProperty(0).toString();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				Bundle b = new Bundle();// 存放数据
				b.putString("result", result);
				msg.setData(b);
				SearchMainActivity.this.handler.sendMessage(msg);
			}
		}
	}
}
