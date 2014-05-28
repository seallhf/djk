package com.example.djk;

import com.example.djk.R;
import com.example.djk.activity.EmergencyTelActivity;
import com.example.djk.activity.QunceMainActivity;
import com.example.djk.activity.SearchMainActivity;
import com.example.djk.activity.TrainMainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private int page;
	LayoutInflater li;

	public ImageAdapter(Context c, int page) {
		li = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.page = page;
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View cell;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			cell = li.inflate(R.layout.imagecell, null);
		} else {
			cell = convertView;
		}
		ImageView imageView = (ImageView) cell.findViewById(R.id.imageView1);
		imageView.setImageResource(mThumbIds[position]);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setPadding(8, 12, 8, 2);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				if (page == 1)
					switch (position) {
					case 0:
						intent.putExtra("method", "getDataApp_Yuliangtable");
						intent.putExtra("page", 0);
						intent.setClass(mContext, TableActivity.class);
						mContext.startActivity(intent);
						break;
					case 1:
						intent.putExtra("method", "getDataApp_Shuiwenzhantable");
						intent.putExtra("page", 1);
						intent.setClass(mContext, TableWaterActivity.class);
						mContext.startActivity(intent);
						break;
					case 2:
						new AlertDialog.Builder(mContext).setTitle("未建图像检测站，暂不适用！")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setPositiveButton("确定", null)
								.setNegativeButton("取消", null).show();
						break;
					case 3:
						intent.setClass(mContext, GpsActivity.class);
						mContext.startActivity(intent);
						break;
					case 4:
						// intent.putExtra("method",
						// "getDataApp_Shuiwenzhantable");
						intent.setClass(mContext, WeatherActivity.class);
						mContext.startActivity(intent);
						break;
					case 5:
						intent.setClass(mContext, TrainMainActivity.class);
						mContext.startActivity(intent);
						break;
					case 6:
						intent.setClass(mContext, UsualListpage.class);
						mContext.startActivity(intent);
						break;
					case 7:
						intent.setClass(mContext, EmergencyTelActivity.class);
						mContext.startActivity(intent);
						break;
					case 8:
						intent.setClass(mContext, SearchMainActivity.class);
						mContext.startActivity(intent);
						break;
					case 9:
						intent.setClass(mContext, QunceMainActivity.class);
						mContext.startActivity(intent);
						break;
					}
				else if (page == 2) {
					Uri content_url;
					switch (position) {
					case 0:
						intent.setAction("android.intent.action.VIEW");    
						content_url = Uri.parse("http://61.184.80.90:90");   
						intent.setData(content_url);  
						mContext.startActivity(intent);
						break;
					case 1:
						intent.setAction("android.intent.action.VIEW");    
						content_url = Uri.parse("http://61.184.80.90:90");   
						intent.setData(content_url);  
						mContext.startActivity(intent);
						break;
					case 2:
						intent.setAction("android.intent.action.VIEW");    
						content_url = Uri.parse("http://61.184.80.90/doc/page/main.asp");   
						intent.setData(content_url);  
						mContext.startActivity(intent);
						break;
					}
				} else if (page == 3) {
					Uri content_url;
					switch (position) {
					case 0:
						intent.setAction("android.intent.action.VIEW");    
						content_url = Uri.parse("http://61.184.80.242:8081/");   
						intent.setData(content_url);  
						mContext.startActivity(intent);
						break;
					case 1:
						intent.setAction("android.intent.action.VIEW");    
						content_url = Uri.parse("http://61.184.80.242:8081/");   
						intent.setData(content_url);  
						mContext.startActivity(intent);
						break;
					case 2:
						intent.setAction("android.intent.action.VIEW");    
						content_url = Uri.parse("http://61.184.80.242:8081/");   
						intent.setData(content_url);  
						mContext.startActivity(intent);
						break;
					}
				}
			}
		});
		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setAlpha((float) 0.5);
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_OUTSIDE:
					v.setAlpha((float) 1);
					break;
				}
				// else if(event.equals(MotionEvent.ACTION_UP))
				// v.setAlpha((float) 1);
				return false;
			}
		});

		TextView textView = (TextView) cell.findViewById(R.id.UsualtextView1);
		textView.setText(mNameIds[position]);
		return cell;
	}

	// references to our images
	private Integer[] mThumbIds;
	private String[] mNameIds;

	public void setImage(Integer[] image) {
		this.mThumbIds = image;
	}

	public void setImageName(String[] name) {
		this.mNameIds = name;
	}

}