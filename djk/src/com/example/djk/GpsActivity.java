package com.example.djk;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.djk.R;
import com.example.djk.activity.RadarListActivity;
import com.example.djk.view.RadarView;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class GpsActivity extends Activity {

	private Location location;
	private boolean scar = false;
	private RadarHandler radarHandler;
	private Button btn;
	private FrameLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		openGPSSettings();
		getLocation();
		layout = (FrameLayout) GpsActivity.this
				.findViewById(R.id.gps_radar_place);
		radarHandler = new RadarHandler();
		layout.removeAllViews();
		layout.addView(new RadarView(GpsActivity.this, -2), new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		btn = (Button) GpsActivity.this.findViewById(R.id.gps_button);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (scar == false) {
					scar = true;
					ViewChanger changer = new ViewChanger();
					new Thread(changer).start();
					Toast.makeText(GpsActivity.this, "����ɨ��,���Ժ󡭡�",
							Toast.LENGTH_SHORT).show();
					btn.setText("ֹͣɨ��");
				} else {
					scar = false;
					btn.setText("ɨ��");
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gps, menu);
		return true;
	}

	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "GPSģ������", Toast.LENGTH_SHORT).show();
			return;
		}

		Toast.makeText(this, "�뿪��GPS��", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		startActivityForResult(intent, 0); // ��Ϊ������ɺ󷵻ص���ȡ����
	}

	LocationManager locationManager;

	String provider;

	private void getLocation() {
		// ��ȡλ�ù������
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) this.getSystemService(serviceName);
		// ���ҵ�������Ϣ
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // �߾���
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // �͹���

		String provider = locationManager.getBestProvider(criteria, true); // ��ȡGPS��Ϣ
		Location location = locationManager.getLastKnownLocation(provider); // ͨ��GPS��ȡλ��
		updateToNewLocation(location);

		// ���ü��������Զ����µ���Сʱ��Ϊ���N��(1��Ϊ1*1000������д��ҪΪ�˷���)����Сλ�Ʊ仯����N��
		locationManager.requestLocationUpdates(provider, 100 * 1000, 500,
				locationListener);

	}

	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		// ��λ�ñ仯ʱ����
		@Override
		public void onLocationChanged(Location location) {
			// ʹ���µ�location����TextView��ʾ
			updateToNewLocation(location);
		}
	};

	private void updateToNewLocation(Location location) {
		this.location = location;
	}

	int theta = 0;

	class DetailDataCacher implements Runnable {

		final String serviceUrl = "http://61.184.84.212:10000/webService.asmx";
		private String method;

		public DetailDataCacher(String method) {
			this.method = method;
		}

		public void run() {
			SoapObject request = new SoapObject("http://tempuri.org/", method);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE ht = new HttpTransportSE(serviceUrl);
			envelope.bodyOut = request;
			String result = "";
			try {
				ht.call("http://tempuri.org/" + method, envelope);
				if (envelope.bodyIn != null) {
					SoapObject soapObject = (SoapObject) envelope.bodyIn;
					result = soapObject.getProperty(0).toString();
					Message msg = new Message();
					Bundle b = new Bundle();// �������
					b.putString("result", result);
					scar = false;
					b.putBoolean("scar", scar);
					msg.setData(b);
					GpsActivity.this.radarHandler.sendMessage(msg); // ��Handler������Ϣ,����UI*/
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class ViewChanger implements Runnable {

		public ViewChanger() {

		}

		public void run() {
			DetailDataCacher detailDataCacher = new DetailDataCacher(
					"getRadarData");
			new Thread(detailDataCacher).start();
			while (scar) {
				Bundle b = new Bundle();// �������
				Message msg = new Message();
				if (theta >= 50)
					theta = 0;
				b.putInt("theta", theta);
				b.putBoolean("scar", scar);
				msg.setData(b);
				GpsActivity.this.radarHandler.sendMessage(msg); // ��Handler������Ϣ,����UI*/
				theta++;
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*
			 * Bundle b = new Bundle();// ������� Message msg = new Message();
			 * b.putInt("theta", theta); b.putBoolean("scar", scar);
			 * msg.setData(b); GpsActivity.this.radarHandler.sendMessage(msg);
			 */
		}
	}

	class RadarHandler extends Handler {

		public RadarHandler() {
		}

		public RadarHandler(Looper L) {
			super(L);
		}

		// ���������д�˷���,��������
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			// �˴����Ը���UI
			Bundle b = msg.getData();
			int theta = b.getInt("theta");
			boolean isScar = b.getBoolean("scar");
			String result = b.getString("result");
			if (isScar) {
				FrameLayout layout = (FrameLayout) GpsActivity.this
						.findViewById(R.id.gps_radar_place);
				RadarView view;
				view = new RadarView(GpsActivity.this, theta);
				layout.removeAllViews();
				layout.addView(view, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			} else if (result != null && !isScar && location != null) {
				btn.setText("ɨ��");
				//result ="61940205|����|0.4|5|1Сʱ������#62039130|̫��Ͽ|0.4|5|1Сʱ������#61939240|�ں�|0.5|2|3Сʱ������";
				if (result.equals("anyType{}"))
					new AlertDialog.Builder(GpsActivity.this).setTitle("Ԥ����Ϣ")
					.setMessage("�������50������δ����ɽ���ֺ�Ԥ����")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("ȷ��", null)
					.setNegativeButton("ȡ��", null).show();
				else {
					Intent intent = new Intent();
					intent.putExtra("result", result);
					intent.putExtra("Weidu", location.getLatitude());
					intent.putExtra("Jingdu", location.getLongitude());
					intent.setClass(GpsActivity.this.getBaseContext(),
							RadarListActivity.class);
					startActivity(intent);
				}
			}
			else if(!isScar&&location == null)
			{
				btn.setText("ɨ��");
				new AlertDialog.Builder(GpsActivity.this).setTitle("Ԥ����Ϣ")
				.setMessage("�޷����GPS���꣡")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("ȷ��", null)
				.setNegativeButton("ȡ��", null).show();
			}
		}
	}
}
