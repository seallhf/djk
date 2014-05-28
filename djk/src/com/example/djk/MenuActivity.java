package com.example.djk;

import com.example.djk.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MenuActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "ȫ��ɽ���ֺ����ƽ̨";
			case 1:
				return "��ɽˮ���Ӽ��ƽ̨";
			case 2:
				return "�˺�ˮ���Ӽ��ƽ̨";
			}
			return null;
		}
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
			int page = (getArguments().getInt(ARG_SECTION_NUMBER));	
			View rootView=null;
	        if(page==1)
	        {
	        	rootView = inflater.inflate(R.layout.fragment_menu_dummy,
						container, false);
	        	GridView gridview = (GridView)rootView.findViewById(R.id.gridView);
				ImageAdapter imageadapter = new ImageAdapter(getActivity(),page);
	        	Integer[] mThumbIds = {
					       R.drawable.rainfall,R.drawable.waterlevel,
					       R.drawable.picture,R.drawable.radar,
					       R.drawable.weather,R.drawable.movie,
					       R.drawable.tabel,R.drawable.alarm,R.drawable.search,
					       R.drawable.qunce
					    };
				String[] mNameIds= {"������","ˮλ���","ͼ����","Ԥ���״�","����Ԥ��","������ѵ","�������","��������","Ѵ���ѯ","Ⱥ�ⱨѴ"};
		        imageadapter.setImage(mThumbIds);
	        	imageadapter.setImageName(mNameIds);
	        	gridview.setAdapter(imageadapter);
	        }
	        else if(page==2)
	        {
	        	rootView = inflater.inflate(R.layout.fragment_menu_dummy,
						container, false);
	        	GridView gridview = (GridView)rootView.findViewById(R.id.gridView);
				ImageAdapter imageadapter = new ImageAdapter(getActivity(),page);
	        	Integer[] mThumbIds = {
					       R.drawable.rainfall,R.drawable.waterlevel,
					       R.drawable.movie
					    };
				String[] mNameIds= {"������","ˮλ","��Ƶ"};
		        imageadapter.setImage(mThumbIds);
	        	imageadapter.setImageName(mNameIds);
	        	gridview.setAdapter(imageadapter); 
	        	/*Integer[] mThumbIds = {
					       R.drawable.rainfall,R.drawable.waterlevel,
					       R.drawable.picture,R.drawable.movie,
					       R.drawable.weather
					    };
				String[] mNameIds= {"������","ˮλ","��Ƭ","��Ƶ","����Ԥ��"};
		        imageadapter.setImage(mThumbIds);
	        	imageadapter.setImageName(mNameIds);*/
	        }
	        else if(page==3)
	        {
	        	rootView = inflater.inflate(R.layout.fragment_menu_dummy,
						container, false);
	        	GridView gridview = (GridView)rootView.findViewById(R.id.gridView);
				ImageAdapter imageadapter = new ImageAdapter(getActivity(),page);
	        	Integer[] mThumbIds = {
	        			   R.drawable.rainfall,R.drawable.waterlevel,
					       R.drawable.movie
					    };
				String[] mNameIds= {"������","ˮλ","��Ƶ"};
		        imageadapter.setImage(mThumbIds);
	        	imageadapter.setImageName(mNameIds);
	        	gridview.setAdapter(imageadapter);
	        }					
			return rootView;
		}
	}

}
