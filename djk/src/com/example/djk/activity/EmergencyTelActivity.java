package com.example.djk.activity;

import com.example.djk.R;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class EmergencyTelActivity extends Activity {

	private TextView TelTextView1;
	private TextView TelTextView2;
	private TextView TelTextView3;
	private TextView TelTextView4;
	private TextView TelTextView5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_tel);
		TelTextView1 = (TextView) findViewById(R.id.TeltextView1);
		TelTextView2 = (TextView) findViewById(R.id.TeltextView2);
		TelTextView3 = (TextView) findViewById(R.id.TeltextView3);
		TelTextView4 = (TextView) findViewById(R.id.TeltextView4);
		TelTextView5 = (TextView) findViewById(R.id.TeltextView5);
		TelTextView1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 意图 要干什么 //获取文本框中的内容 phone.getText();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:0719-5223247"));
				// 开始处理意图 执行
				EmergencyTelActivity.this.startActivity(intent);
			}
		});
		TelTextView2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 意图 要干什么 //获取文本框中的内容 phone.getText();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:12121"));
				// 开始处理意图 执行
				EmergencyTelActivity.this.startActivity(intent);
			}
		});
		TelTextView3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 意图 要干什么 //获取文本框中的内容 phone.getText();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:119"));
				// 开始处理意图 执行
				EmergencyTelActivity.this.startActivity(intent);
			}
		});
		TelTextView4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 意图 要干什么 //获取文本框中的内容 phone.getText();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:110"));
				// 开始处理意图 执行
				EmergencyTelActivity.this.startActivity(intent);
			}
		});
		TelTextView5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 意图 要干什么 //获取文本框中的内容 phone.getText();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:120"));
				// 开始处理意图 执行
				EmergencyTelActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.emergency_tel, menu);
		return true;
	}

}
