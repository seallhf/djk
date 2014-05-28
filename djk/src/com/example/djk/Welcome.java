package com.example.djk;

import java.io.FileOutputStream;
import java.io.IOException;
import android.content.SharedPreferences;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.djk.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class Welcome extends Activity {

	private Handler mHandler;
	private LoadingView main_imageview;	
	public String result;
	private SharedPreferences sp;
	@SuppressWarnings("static-access")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉状态栏
		setContentView(R.layout.activity_welcome);	
		main_imageview = (LoadingView)findViewById(R.id.main_imageview);
		sp = getSharedPreferences("djkfx", this.MODE_PRIVATE);//更新APP状态，是否为第一次登陆等
		initView();
	}

	public void initView() {
		boolean a= sp.getBoolean("isStart", false);//修改当前登陆状态
		if (a) {// 如果正在后台运行
			goMainActivity();
		}
		else
		{
			initLoadingImages();
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean("isStart", true);
			editor.commit();
			DataCacher data= new DataCacher("getDataApp_Main","");
			new Thread(data).start();
			mHandler = new Handler();
			main_imageview.startAnim();
			mHandler.postDelayed(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					goMainActivity();
				}
			}, 5000);
		}
	}
	
	private void initLoadingImages()
    {
        int[] imageIds = new int[6];
        imageIds[0] = R.drawable.loader_frame_1;
        imageIds[1] = R.drawable.loader_frame_2;
        imageIds[2] = R.drawable.loader_frame_3;
        imageIds[3] = R.drawable.loader_frame_4;
        imageIds[4] = R.drawable.loader_frame_5;
        imageIds[5] = R.drawable.loader_frame_6;        
        main_imageview.setImageIds(imageIds);
    }

	/**
	 * 进入登陆界面
	 */
	public void goMainActivity() {
		Intent intent = new Intent();
		intent.setClass(this, MenuActivity.class);
		intent.putExtra("data", result);
		startActivity(intent);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	class DataCacher implements Runnable {
  		
  		final String serviceUrl = "http://61.184.84.212:10000/webService.asmx";
  		private String method;
  		private String location;
  		public DataCacher(String method,String location)
  		{
  			this.method=method;
  			this.location=location;
  		} 		
  		
  		public void run() {    	  			
    	  	SoapObject request = new SoapObject("http://tempuri.org/", method);
    	  	if(!location.equals(""))
    	  		request.addProperty("location",location);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);  
	    	envelope.dotNet=true;	    	
	    	envelope.setOutputSoapObject(request);		    	
	    	HttpTransportSE ht = new HttpTransportSE(serviceUrl);
	    	envelope.bodyOut=request;
	    	try
	    	{
	    		ht.call("http://tempuri.org/"+method, envelope);
	    		if (envelope.bodyIn!=null)
	    		{
	    			SoapObject soapObject=(SoapObject) envelope.bodyIn;
	    			result = soapObject.getProperty(0).toString();
	    			FileOutputStream wr = openFileOutput("mainData.data",MODE_PRIVATE);
	    			wr.write(result.getBytes());
	    			wr.close();
	    		}
	    	}
	    	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }		
          /*Message msg = new Message();
          Bundle b = new Bundle();// 存放数据
          b.putString("result", result);
          msg.setData(b);
          Welcome.this.myHandler.sendMessage(msg); // 向Handler发送消息,更新UI*/
      }
  }	
}
