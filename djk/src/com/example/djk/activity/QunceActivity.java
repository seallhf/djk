package com.example.djk.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.djk.R;
import com.example.djk.R.layout;
import com.example.djk.R.menu;
import com.example.djk.activity.QunceMainActivity.MyHandler;
import com.example.djk.activity.SearchMainActivity.DetailDataCacher;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class QunceActivity extends Activity {

	private String phoneNum;
	private Button btnSent;
	private Spinner snrStreet;
	private Spinner snrStartHour;
	private Spinner snrEndHour;
	private EditText startDate;
	private EditText endDate;
	private EditText cun;
	private EditText jiangyuliang;
	private MyHandler handler;

	private AlertDialog dialog;
	private AlertDialog.Builder alert ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qunce);
		Intent intent = getIntent();
		phoneNum = intent.getStringExtra("phoneNum");
		btnSent = (Button) this.findViewById(R.id.SentButton);
		snrStreet = (Spinner) this.findViewById(R.id.qunce_street);
		snrStartHour = (Spinner) this.findViewById(R.id.StartHourSpinner);
		snrEndHour = (Spinner) this.findViewById(R.id.EndHourSpinner);
		startDate = (EditText) this.findViewById(R.id.QunceStartText);
		endDate = (EditText) this.findViewById(R.id.QunceEndText);
		jiangyuliang = (EditText) this.findViewById(R.id.jiangyuliangText);
		cun = (EditText) this.findViewById(R.id.qunceCunText);
		handler = new MyHandler();
		alert = new AlertDialog.Builder(QunceActivity.this).setTitle("群测报讯")
				.setMessage("正在报讯，请稍后……")
				.setIcon(android.R.drawable.ic_dialog_info);			
		btnSent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!jiangyuliang.getText().toString().equals("")) {
					Date start;
					Date end;
					String startHour = snrStartHour.getSelectedItem()
							.toString();
					String endHour = snrEndHour.getSelectedItem().toString();
					if (!endDate.getText().toString().equals("")
							|| !startDate.getText().toString().equals("")) {
						try {
							start = new SimpleDateFormat("yyyy-MM-dd")
									.parse(startDate.getText().toString());
							end = new SimpleDateFormat("yyyy-MM-dd")
									.parse(endDate.getText().toString());
							if (start.after(end))
								Toast.makeText(QunceActivity.this,
										"起点时间在终点时间之后，请正确输入！", 2000).show();
							else if (start.equals(end)) {
								if (Integer.parseInt(startHour) >= Integer
										.parseInt(endHour))
									Toast.makeText(QunceActivity.this,
											"起点时间在终点时间之后，请正确输入！", 2000).show();
							} else {
								Calendar c = Calendar.getInstance();
								c.setTime(start);
								c.set(Calendar.HOUR_OF_DAY,
										Integer.parseInt(startHour));
								start = c.getTime();
								c.setTime(end);
								c.set(Calendar.HOUR_OF_DAY,
										Integer.parseInt(endHour));
								end = c.getTime();
								DetailDataCacher dc = new DetailDataCacher(
										"sendXunqing", phoneNum, snrStreet
												.getSelectedItem().toString(),
										new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss")
												.format(start),
										new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss")
												.format(end), cun.getText()
												.toString(), jiangyuliang
												.getText().toString());
								dialog = alert.show();
								new Thread(dc).start();
							}
						} catch (Exception e) {
						}
					}
				} else
					Toast.makeText(QunceActivity.this, "请输入降雨量！", 2000).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qunce, menu);
		return true;
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
			String detaildata = b.getString("result");
			if (detaildata.equals("ok")) {
				dialog.dismiss();
				new AlertDialog.Builder(QunceActivity.this).setTitle("群测报讯")
						.setMessage("报讯成功！")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定", null)
						.setNegativeButton("取消", null).show();
			} else {
				new AlertDialog.Builder(QunceActivity.this).setTitle("群测报讯")
						.setMessage("对不起，系统产生错误！")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定", null)
						.setNegativeButton("取消", null).show();
			}
		}
	}

	class DetailDataCacher implements Runnable {

		final String serviceUrl = "http://61.184.84.212:10000/webService.asmx";
		private String method;
		private String phone;
		private String street;
		private String start;
		private String end;
		private String cun;
		private String jiangyuliang;

		public DetailDataCacher(String method, String phone, String street,
				String start, String end, String cun, String jiangyuliang) {
			this.method = method;
			this.phone = phone;
			this.street = street;
			this.start = start;
			this.end = end;
			this.cun = cun;
			this.jiangyuliang = jiangyuliang;
		}

		public void run() {
			{			
				String result = "";
				SoapObject request = new SoapObject("http://tempuri.org/",
						method);

				request.addProperty("phone", phone);
				request.addProperty("street", street);
				request.addProperty("_start", start);
				request.addProperty("_end", end);
				request.addProperty("cun", cun);
				request.addProperty("jiangyuliang", jiangyuliang);
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
				QunceActivity.this.handler.sendMessage(msg);
			}
		}
	}
}
