package com.example.djk.activity;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.djk.R;
import com.example.djk.TableActivity;
import com.example.djk.activity.SearchMainActivity.MyHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QunceMainActivity extends Activity {

	private Button btn;
	private LinearLayout lt;
	private EditText txt;
	private MyHandler handler;
	private String phoneNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qunce_main);
		lt = (LinearLayout) this.findViewById(R.id.qunce_layout);
		txt = (EditText) this.findViewById(R.id.qunce_phone);
		btn = (Button) this.findViewById(R.id.qunce_ok);
		handler = new MyHandler();
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView txtView = new TextView(QunceMainActivity.this);
				txtView.setText("正在查询，请等待……");
				lt.removeAllViews();
				lt.addView(txtView);
				if (!txt.getText().toString().equals("")) {
					phoneNum=txt.getText().toString();
					DetailDataCacher dc = new DetailDataCacher("getAuthority", txt.getText().toString());
					new Thread(dc).start();
				}
				else
					Toast.makeText(QunceMainActivity.this,
							"请输入报讯员电话号码！", 2000).show();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.qunce_main, menu);
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
				Intent intent = new Intent();
				intent.putExtra("phoneNum", phoneNum);
				intent.setClass(QunceMainActivity.this, QunceActivity.class);
				QunceMainActivity.this.startActivity(intent);
			} else {
				new AlertDialog.Builder(QunceMainActivity.this)
						.setTitle("群测报讯").setMessage("对不起，您不具有报讯的权限！")
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

		public DetailDataCacher(String method, String phone) {
			this.method = method;
			this.phone = phone;
		}

		public void run() {
			{
				String result = "";
				SoapObject request = new SoapObject("http://tempuri.org/",
						method);
				if (!phone.equals("")) {
					request.addProperty("phone", phone);
				}
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
				QunceMainActivity.this.handler.sendMessage(msg);
			}
		}
	}

}
