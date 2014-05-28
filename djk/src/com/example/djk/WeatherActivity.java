package com.example.djk;

import com.example.djk.R;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WeatherActivity extends Activity {

	private WebView mWebViewTitle;
	private WebView mWebViewDetail;
	private Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		mWebViewTitle = (WebView) findViewById(R.id.Weathertitle);
		WebSettings wb = mWebViewTitle.getSettings();
		wb.setJavaScriptEnabled(true);
		mWebViewDetail = (WebView) findViewById(R.id.weatherdetail);
		mWebViewTitle.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){       
                view.loadUrl(url);       
                return true;       
            }
        });
		mWebViewDetail.setWebViewClient(new WebViewClient(){       
            public boolean shouldOverrideUrlLoading(WebView view, String url){       
                view.loadUrl(url);       
                return true;       
            }
        });
		mWebViewTitle.loadUrl("http://61.184.84.212:8086/weatherstyle1.html"); 
		mWebViewDetail.loadUrl("http://www.weather.com.cn/weather/101201107.shtml");		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather, menu);
		return true;
	}

}
