package com.example.djk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.djk.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class TableActivity extends Activity {

	FrameLayout lt; 
	ProgressBar psb;
	TextView load;
	int page;
	public MyHandler handler;
	String result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table);
		handler = new MyHandler();
		lt = (FrameLayout) findViewById(R.id.tablelist);
		LoadingPage();
		Intent intent=getIntent();
		String method = intent.getStringExtra("method");
		page = intent.getIntExtra("page", 0);
		DetailDataCacher datacacher=new DetailDataCacher(method,"",0);
		new Thread(datacacher).start();
	}

	//更新数据页面
	private void LoadingPage()
	{
		load=(TextView)this.findViewById(R.id.loadingtext);
		psb = (ProgressBar)this.findViewById(R.id.progressBar1);
		psb.setMax(100);
	  	psb.setProgress(0);
	  	psb.setSecondaryProgress(0);
        psb.setVisibility(View.VISIBLE);            
        new Thread(){
        	public void run()
        	{
        		int flag=0;
        		while(true)
        		{	
        			try
        			{
        				Thread.sleep(100);
        				psb.setProgress(flag);
        				if(psb.getProgress()==100)
        				{
        					psb.setProgress(5);
        					flag=5;
        				}
        				flag+=5;
        			}
        			catch (InterruptedException e)
        			{
        				e.printStackTrace();
        			}
        		}
        	}
        }.start();
        load.setText("Loading...");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.table, menu);
		return true;
	}

	class DetailDataCacher implements Runnable {
  		
  		final String serviceUrl = "http://61.184.84.212:10000/webService.asmx";
  		private String method;
  		private String location;
  		private int num;
  		
  		public DetailDataCacher(String method,String location,int num)
  		{
  			this.method=method;
  			this.location=location;
  			this.num=num;
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
	    		}
	    	}
	    	catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }    	  
          Message msg = new Message();
          Bundle b = new Bundle();// 存放数据
          b.putString("result", result);
          b.putInt("num", num);
          msg.setData(b);
          TableActivity.this.handler.sendMessage(msg); // 向Handler发送消息,更新UI*/    	  
       }
	}
	
	class MyHandler extends Handler {
		
		List<Map<String,String>> datalist;
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }

        public List<Map<String, String>> praseDataRainfall()
		{
			List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		    if(result!=null)
		    {
		    	String[] lines = result.split("#");
		    	int i =0;
		    	while(i<lines.length)
		    	{
		    		String line =lines[i++];
		    		String[] elems=line.split("\\|");
		    		String id = elems[0];
		    		Map<String,String> v= new HashMap<String, String>();
		    		v.put("RcurrentTIME", elems[1]);
		    		v.put("id", id);
		    		v.put("name", elems[7]);
		    		v.put("tviewrain1", elems[2]+"mm");
		    		v.put("tviewrain3", elems[3]+"mm");
		    		v.put("tviewrain6", elems[4]+"mm");
		    		v.put("tviewrain12", elems[5]+"mm");
		    		v.put("tviewrain24", elems[6]+"mm");
		    		values.add(v);
		    	}
		    }		   
			return values;
		}
        
        // 子类必须重写此方法,接受数据
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            // 此处可以更新UI
            Bundle b = msg.getData();
            String detaildata = result;
            int num=b.getInt("num");
            lt = (FrameLayout)TableActivity.this.findViewById(R.id.tablelist);
        	lt.removeAllViews();
        	ListView dataview = new ListView(TableActivity.this);
        	datalist=praseDataRainfall();
        	dataview.setOnItemClickListener(new OnItemClickListener()
			   {
			       @SuppressWarnings("rawtypes")
				public void onItemClick(AdapterView adapterView, View view,int arg2, long arg3) 
			       {
			          int selectedPosition = arg2; 
			          Intent intent = new Intent();
			          intent.putExtra("page", page);
			          intent.putExtra("id", ((Map<String,String>)datalist.get(selectedPosition)).get("id"));
			          intent.setClass(TableActivity.this,ChartActivity.class);
			          startActivity(intent);
			       }
			   });
        	String[] ColumnNames=new String[]{"name","RcurrentTIME","tviewrain1","tviewrain3","tviewrain6","tviewrain12","tviewrain24"};
        	ListAdapter adapter = new MySimpleAdapter(TableActivity.this,R.layout.layout_listview, datalist,ColumnNames, new int[] { R.id.name,R.id.Rlastedtime, 
        	R.id.tviewrain1, R.id.tviewrain3, R.id.tviewrain6, R.id.tviewrain12,R.id.tviewrain24}); 
        	dataview.setAdapter(adapter);
        	lt.addView(dataview,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        	//layout.bringChildToFront(chartView);
        }
    }
}
