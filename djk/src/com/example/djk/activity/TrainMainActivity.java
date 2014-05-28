package com.example.djk.activity;

import com.example.djk.R;
import com.example.djk.view.ImageSwitchView;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TrainMainActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	public MyHandler handler;

	public static ImageSwitchView view;

	public int length;
	public int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train_main);
		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		handler = new MyHandler();
		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown
		// list.
				new ArrayAdapter<String>(getActionBarThemedContextCompat(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] { "山洪灾害宣传",
								"山洪灾害防御常识", "防洪抢险注意事项", "视频宣传", }), this);
	}

	/**
	 * Backward-compatible version of {@link ActionBar#getThemedContext()} that
	 * simply returns the {@link android.app.Activity} if
	 * <code>getThemedContext</code> is unavailable.
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private Context getActionBarThemedContextCompat() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return getActionBar().getThemedContext();
		} else {
			return this;
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.train_main, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new DummySectionFragment();
		int page = position;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		
		if (page == 3) {
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri
					.parse("http://v.youku.com/v_show/id_XNjg1NTkxODQw.html");
			intent.setData(content_url);
			startActivity(intent);
		} else 
		
		{
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("page", page);
			msg.setData(b);
			TrainMainActivity.this.handler.sendMessage(msg); // 向Handler发送消息,更新UI*/
		}
		return true;
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_train_main_dummy, container, false);
			view = (ImageSwitchView) rootView
					.findViewById(R.id.train_imageview);

			return rootView;
		}

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
			Bundle b = msg.getData();
			// 此处可以更新UI
			int page = b.getInt("page");
			RelativeLayout layout = (RelativeLayout) TrainMainActivity.this
					.findViewById(R.id.dummy_train);
			{
				switch (page) {
				case 0:
					view.setImageIds(new int[] { R.drawable.s1_1,
							R.drawable.s1_2, R.drawable.s1_3, R.drawable.s1_4,
							R.drawable.s1_5, R.drawable.s1_6, R.drawable.s1_7,
							R.drawable.s1_8, R.drawable.s1_9, R.drawable.s1_10,
							R.drawable.s1_11, R.drawable.s1_12 });
					break;
				case 1:
					view.setImageIds(new int[] { R.drawable.s2_1,
							R.drawable.s2_2, R.drawable.s2_3 });
					break;
				case 2:
					view.setImageIds(new int[] { R.drawable.s3_1,
							R.drawable.s3_2, R.drawable.s3_3, R.drawable.s3_4,
							R.drawable.s3_5 });
					break;
				}

			}
			view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (event.getX() > (v.getWidth() * 5 / 7)) {
							if (view.index < view.length - 1) {
								view.index++;
								view.setImage(view.index);
							} else
								Toast.makeText(TrainMainActivity.this, "已到末页！",
										Toast.LENGTH_SHORT).show();
						} else if (event.getX() < (v.getWidth() * 2 / 7)) {
							if (view.index > 0) {
								view.index--;
								view.setImage(view.index);
							} else
								Toast.makeText(TrainMainActivity.this, "已到首页！",
										Toast.LENGTH_SHORT).show();
						} else {
							return false;
						}
						break;
					}
					return false;
				}
			});
			layout.removeAllViews();
			layout.addView(view);
		}
	}
}
