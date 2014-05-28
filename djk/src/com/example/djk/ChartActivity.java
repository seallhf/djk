package com.example.djk;

import java.io.FileOutputStream;
import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.djk.R;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ChartActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	public static ProgressBar psb;
	public MyHandler handler;
	public String location;
	public String result;
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		handler = new MyHandler();
		Intent intent = getIntent();
		location = intent.getStringExtra("id");
		int page = intent.getIntExtra("page", 0);
		DetailDataCacher data = new DetailDataCacher("getData", location, 0);
		new Thread(data).start();
		// Set up the dropdown list navigation in the action bar.
		if (page == 0) {
			actionBar
					.setListNavigationCallbacks(
							// Specify a SpinnerAdapter to populate the dropdown
							// list.
							new ArrayAdapter<String>(
									getActionBarThemedContextCompat(),
									android.R.layout.simple_list_item_1,
									android.R.id.text1, new String[] {
											"1小时降雨量", "3小时降雨量", "6小时降雨量",
											"12小时降雨量", "24小时降雨量" }), this);
		} else if (page == 1) {
			actionBar.setListNavigationCallbacks(
			// Specify a SpinnerAdapter to populate the dropdown list.
					new ArrayAdapter<String>(getActionBarThemedContextCompat(),
							android.R.layout.simple_list_item_1,
							android.R.id.text1, new String[] { "1小时降雨量",
									"3小时降雨量", "6小时降雨量", "12小时降雨量", "24小时降雨量",
									"当月水位" }), this);
		}
	}

	/**
	 * Backward-compatible version of {@link ActionBar#getThemedContext()} that
	 * simply returns the {@link android.app.Activity} if
	 * <code>getThemedContext</code> is unavailable.
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private Context getActionBarThemedContextCompat() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return getActionBar().getThemedContext();
		} else {
			return this;
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		DetailDataCacher data = new DetailDataCacher("getData", location,
				position);
		new Thread(data).start();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
		// args.putString(DummySectionFragment.data,result);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		return true;
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static final String data = "data";

		public DummySectionFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_chart_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			psb = (ProgressBar) rootView.findViewById(R.id.progressBar3);
			psb.setMax(100);
			psb.setProgress(0);
			psb.setSecondaryProgress(0);
			psb.setVisibility(View.VISIBLE);
			new Thread() {
				public void run() {
					int flag = 0;
					while (true) {
						try {
							Thread.sleep(100);
							psb.setProgress(flag);
							if (psb.getProgress() == 100) {
								psb.setProgress(5);
								flag = 5;
							}
							flag += 5;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
			dummyTextView.setText("Loading...");
			/*
			 * if(detaildata!=null) { AverageTemperatureChart a = new
			 * AverageTemperatureChart(); View
			 * chartView=a.getView(getActivity(),
			 * detaildata,getArguments().getInt(ARG_SECTION_NUMBER));
			 * //RelativeLayout layout =
			 * (RelativeLayout)getActivity().findViewById
			 * (R.layout.fragment_chart_dummy); //layout.addView(chartView);
			 * return chartView; }
			 */
			return rootView;
		}
	}

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
			String detaildata =b.getString("result");
			int num = b.getInt("num");
			AverageTemperatureChart a = new AverageTemperatureChart();
			View chartView = a.getView(ChartActivity.this, detaildata, num,location);
			RelativeLayout layout = (RelativeLayout) ChartActivity.this
					.findViewById(R.id.dummy_chart);
			layout.removeAllViews();
			layout.addView(chartView, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			// layout.bringChildToFront(chartView);
		}
	}

	class DetailDataCacher implements Runnable {

		final String serviceUrl = "http://61.184.84.212:10000/webService.asmx";
		private String method;
		private String location;
		private int num;

		public DetailDataCacher(String method, String location, int num) {
			this.method = method;
			this.location = location;
			this.num = num;
		}

		public void run() {
			if (result == null) {
				SoapObject request = new SoapObject("http://tempuri.org/",
						method);
				if (!location.equals(""))
					request.addProperty("location", location);
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
			}
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putString("result", result);
			b.putInt("num", num);
			msg.setData(b);
			ChartActivity.this.handler.sendMessage(msg); // 向Handler发送消息,更新UI*/
		}
	}
}
