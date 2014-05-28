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
import com.example.djk.TableActivity.DetailDataCacher;
import com.example.djk.TableActivity.MyHandler;
import com.example.djk.util.ShuikuLevel;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
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

public class TableWaterActivity extends Activity {

	
	
	
	FrameLayout lt; 
	ProgressBar psb;
	TextView load;
	public MyHandler handler;
	String result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_water);
		handler = new MyHandler();
		lt = (FrameLayout) findViewById(R.id.tablelist1);
		LoadingPage();
		Intent intent=getIntent();
		String method = intent.getStringExtra("method");
		DetailDataCacher datacacher=new DetailDataCacher(method,"",0);
		new Thread(datacacher).start();
	}

	//更新数据页面
	private void LoadingPage()
	{
		load=(TextView)this.findViewById(R.id.loadingtext1);
		psb = (ProgressBar)this.findViewById(R.id.progressBar2);
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
		getMenuInflater().inflate(R.menu.table_water, menu);
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
	    			FileOutputStream wr = openFileOutput("Water.data",MODE_PRIVATE);
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
          Message msg = new Message();
          Bundle b = new Bundle();// 存放数据
          b.putString("result", result);
          b.putInt("num", num);
          msg.setData(b);
          TableWaterActivity.this.handler.sendMessage(msg); // 向Handler发送消息,更新UI*/    	  
       }
	}
	
	class MyHandler extends Handler {
		
		List<Map<String,String>> datalist ;

		String date ="";
		
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }

        public List<Map<String, String>> praseDataWater()
		{
			List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		    if(result!=null)
		    {
		    	String[] lines = result.split("#");
		    	System.out.println(result);
		    	int i =0;
		    	while(i<lines.length)
		    	{
		    		String line =lines[i++];
		    		String[] elems=line.split("\\|");
		    		String id = elems[0];
		    		Map<String,String> v= new HashMap<String, String>();
		    	    String op = elems[5];
		    		v.put("id", id);
		    		v.put("_name", elems[4]);
		    		if(!elems[1].equals("数据缺失"))
	    				v.put("tviewwater0", elems[1]+"m");
	    			else
	    				v.put("tviewwater0", elems[1]);
		    		if(op.equals("1"))
		            {
		    			if(!elems[2].equals("数据缺失"))
		    				v.put("tviewwater8", elems[2]+"m");
		    			else
		    				v.put("tviewwater8", elems[2]);
		    			if(!elems[3].equals("数据缺失"))
		    				v.put("tviewwater16", elems[3]+"m");
		    			else
		    				v.put("tviewwater16", elems[3]);	            	
		            }
		            else if(op.equals("2"))
		            {            	
		            	if(!elems[2].equals("数据缺失"))
		    				v.put("tviewwater8", elems[2]+"m");
		    			else
		    				v.put("tviewwater8", elems[2]);
		            	if(!elems[3].equals("数据缺失"))
		    				v.put("tviewwater16", elems[3]+"m");
		    			else
		    				v.put("tviewwater16", elems[3]);	    
		            }
		            else
		            {
		            	if(!elems[2].equals("数据缺失"))
		    				v.put("tviewwater8", elems[2]+"m");
		    			else
		    				v.put("tviewwater8", elems[2]);
		    			if(!elems[3].equals("数据缺失"))
		    				v.put("tviewwater16", elems[3]+"m");
		    			else
		    				v.put("tviewwater16", elems[3]);
		            }
		    		v.put("currentTIME", elems[6]);
		    		v.put("xunxian", ShuikuLevel.xunxianlevel.get(id)+"m");
		    		v.put("zhunbei", ShuikuLevel.zhunbeizhuanyilevel.get(id)+"m");
		    		v.put("like", ShuikuLevel.likezhuanyilevel.get(id)+"m");		    		
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
            if(detaildata==null)
            {
            	File f = new File(getFilesDir()+"/Water.data");
    			if(f.exists())
    			{
    				try {
    					BufferedReader br=new BufferedReader((new InputStreamReader(new FileInputStream(f))));
    					detaildata = br.readLine();
    				} catch (UnsupportedEncodingException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (FileNotFoundException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    			}
            }
            int num=b.getInt("num");                     
            lt = (FrameLayout)TableWaterActivity.this.findViewById(R.id.tablelist1);
            TextView txt8=(TextView)lt.findViewById(R.id.tviewwater8);
            TextView txt16 = (TextView)lt.findViewById(R.id.tviewwater16);
        	lt.removeAllViews();
        	ListView dataview = new ListView(TableWaterActivity.this);
        	datalist=praseDataWater();
        	
        	dataview.setOnItemClickListener(new OnItemClickListener()
			   {
			       public void onItemClick(AdapterView adapterView, View view,int arg2, long arg3) 
			       {
			          int selectedPosition = arg2; 
			          Intent intent = new Intent();
			          intent.putExtra("page", 1);
			          intent.putExtra("id", ((Map<String,String>)datalist.get(selectedPosition)).get("id"));
			          intent.setClass(TableWaterActivity.this,ChartActivity.class);
			          startActivity(intent);
			       }
			   });
        	String[] ColumnNames=new String[]{"_name","currentTIME","tviewwater0","tviewwater8","tviewwater16","xunxian","zhunbei","like"};
        	ListAdapter adapter = new MySimpleAdapter(TableWaterActivity.this,R.layout.layout_listview_water, praseDataWater(),ColumnNames, new int[] { R.id._name,R.id.lastedTime, 
        	R.id.tviewwater0, R.id.tviewwater8, R.id.tviewwater16,R.id.xianxunlevel,R.id.zhunbeizhuanyilevel,R.id.likezhuanyilevel}); 
        	dataview.setAdapter(adapter);
        	lt.addView(dataview,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        	//layout.bringChildToFront(chartView);
        }
    }
}
