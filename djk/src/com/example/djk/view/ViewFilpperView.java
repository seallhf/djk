package com.example.djk.view;

import com.example.djk.R;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

public class ViewFilpperView extends View implements OnGestureListener,
		OnDoubleTapListener {

	private ViewFlipper mViewFilpper;
	private GestureDetector mGestureDetector;

	@SuppressWarnings("deprecation")
	public ViewFilpperView(Context context,int page) {
		super(context);
		// TODO Auto-generated constructor stub
		
		switch(page)
		{
			case 0:
				mViewFilpper = (ViewFlipper)findViewById(R.id.view_flipper);
				break;
			case 1:
				mViewFilpper = (ViewFlipper)findViewById(R.id.view_flipper2);
				break;
			case 2:
				mViewFilpper = (ViewFlipper)findViewById(R.id.view_flipper3);
				break;
		}
		mGestureDetector = new GestureDetector(this); 
		mViewFilpper.startFlipping();  
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		if(mViewFilpper.isFlipping()){  
            mViewFilpper.stopFlipping();  
        }else{  
            mViewFilpper.startFlipping();  
        }  
        return true; 	
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if (e1.getX() > e2.getX() + 10) {
			mViewFilpper.showNext();
		} else if (e1.getX() < e2.getX() + 10) {
			mViewFilpper
					.setInAnimation(this.getContext(), R.anim.push_right_in);
			mViewFilpper.setOutAnimation(this.getContext(),
					R.anim.push_right_out);
			mViewFilpper.showPrevious();
			mViewFilpper.setInAnimation(this.getContext(), R.anim.push_left_in);
			mViewFilpper.setOutAnimation(this.getContext(),
					R.anim.push_left_out);
		} else {
			return false;
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
