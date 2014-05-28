package com.example.djk;

import com.example.djk.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class UsualListpage extends Activity {

	private TextView UsualTextView1;
	private TextView UsualTextView2;
	private TextView UsualTextView3;
	private TextView UsualTextView4;
	private TextView UsualTextView5;
	private TextView UsualTextView6;
	private TextView UsualTextView7;
	private TextView UsualTextView8;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usual_listpage);
		UsualTextView1=(TextView) findViewById(R.id.UsualtextView1);
		UsualTextView2=(TextView) findViewById(R.id.UsualtextView2);
		UsualTextView3=(TextView) findViewById(R.id.UsualtextView3);
		UsualTextView4=(TextView) findViewById(R.id.UsualtextView4);
		UsualTextView5=(TextView) findViewById(R.id.UsualtextView5);
		UsualTextView6=(TextView) findViewById(R.id.UsualtextView6);
		UsualTextView7=(TextView) findViewById(R.id.UsualtextView7);
		UsualTextView8=(TextView) findViewById(R.id.UsualtextView8);
		UsualTextView1.setOnClickListener(new OnClickListener()
		   {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse("http://61.184.84.212:8081/system/login!input.action");   
				intent.setData(content_url);  
				startActivity(intent);
			}
		   });
		UsualTextView2.setOnClickListener(new OnClickListener()
		   {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse("http://61.184.80.242:8081/");   
				intent.setData(content_url);  
				startActivity(intent);
			}
		   });
		UsualTextView3.setOnClickListener(new OnClickListener()
		   {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse("http://61.184.80.90/doc/page/main.asp");   
				intent.setData(content_url);  
				startActivity(intent);
			}
		   });
		UsualTextView4.setOnClickListener(new OnClickListener()
		   {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse("http://218.200.157.18/(S(5cwy4ihaiefjmhlihf0j4pra))/default.aspx");   
				intent.setData(content_url);  
				startActivity(intent);
			}
		   });
		UsualTextView5.setOnClickListener(new OnClickListener()
		   {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse("http://zdz.hbqx.gov.cn/");   
				intent.setData(content_url);  
				startActivity(intent);
			}
		   });
		UsualTextView6.setOnClickListener(new OnClickListener()
		   {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse("http://www.nmc.gov.cn/publish/forecasts.htm");   
				intent.setData(content_url);  
				startActivity(intent);
			}
		   });
		UsualTextView7.setOnClickListener(new OnClickListener()
		   {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse("http://61.184.84.212:8086/");   
				intent.setData(content_url);  
				startActivity(intent);
			}
		   });
		UsualTextView8.setOnClickListener(new OnClickListener()
		   {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();        
				intent.setAction("android.intent.action.VIEW");    
				Uri content_url = Uri.parse("http://www.cjh.com.cn/");   
				intent.setData(content_url);  
				startActivity(intent);
			}
		   });
		
		UsualTextView1.setOnTouchListener(new OnTouchListener()
        {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						v.setAlpha((float) 0.5);
						break;
					case MotionEvent.ACTION_CANCEL:
				    case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_OUTSIDE:
						v.setAlpha((float) 1);
						break;
				}					
				//else if(event.equals(MotionEvent.ACTION_UP))
					//v.setAlpha((float) 1);
				return false;
			}			
        });
		UsualTextView2.setOnTouchListener(new OnTouchListener()
        {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						v.setAlpha((float) 0.5);
						break;
					case MotionEvent.ACTION_CANCEL:
				    case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_OUTSIDE:
						v.setAlpha((float) 1);
						break;
				}					
				//else if(event.equals(MotionEvent.ACTION_UP))
					//v.setAlpha((float) 1);
				return false;
			}			
        });
		UsualTextView3.setOnTouchListener(new OnTouchListener()
        {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						v.setAlpha((float) 0.5);
						break;
					case MotionEvent.ACTION_CANCEL:
				    case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_OUTSIDE:
						v.setAlpha((float) 1);
						break;
				}					
				//else if(event.equals(MotionEvent.ACTION_UP))
					//v.setAlpha((float) 1);
				return false;
			}			
        });
		UsualTextView4.setOnTouchListener(new OnTouchListener()
        {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						v.setAlpha((float) 0.5);
						break;
					case MotionEvent.ACTION_CANCEL:
				    case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_OUTSIDE:
						v.setAlpha((float) 1);
						break;
				}					
				//else if(event.equals(MotionEvent.ACTION_UP))
					//v.setAlpha((float) 1);
				return false;
			}			
        });
		UsualTextView5.setOnTouchListener(new OnTouchListener()
        {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						v.setAlpha((float) 0.5);
						break;
					case MotionEvent.ACTION_CANCEL:
				    case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_OUTSIDE:
						v.setAlpha((float) 1);
						break;
				}					
				//else if(event.equals(MotionEvent.ACTION_UP))
					//v.setAlpha((float) 1);
				return false;
			}			
        });
		UsualTextView6.setOnTouchListener(new OnTouchListener()
        {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						v.setAlpha((float) 0.5);
						break;
					case MotionEvent.ACTION_CANCEL:
				    case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_OUTSIDE:
						v.setAlpha((float) 1);
						break;
				}					
				//else if(event.equals(MotionEvent.ACTION_UP))
					//v.setAlpha((float) 1);
				return false;
			}			
        });
		UsualTextView7.setOnTouchListener(new OnTouchListener()
        {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						v.setAlpha((float) 0.5);
						break;
					case MotionEvent.ACTION_CANCEL:
				    case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_OUTSIDE:
						v.setAlpha((float) 1);
						break;
				}					
				//else if(event.equals(MotionEvent.ACTION_UP))
					//v.setAlpha((float) 1);
				return false;
			}			
        });
		UsualTextView8.setOnTouchListener(new OnTouchListener()
        {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						v.setAlpha((float) 0.5);
						break;
					case MotionEvent.ACTION_CANCEL:
				    case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_OUTSIDE:
						v.setAlpha((float) 1);
						break;
				}					
				//else if(event.equals(MotionEvent.ACTION_UP))
					//v.setAlpha((float) 1);
				return false;
			}			
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usual_listpage, menu);
		return true;
	}

}
