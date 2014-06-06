package edu.ucsb.cs.cs185.seatracing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class BoatsetCreateActivity extends FragmentActivity {
	
	public static final int NEW_RACE_NEW_LINEUP = 1;
	public static final int OLD_RACE_NEW_LINEUP = 2;
	
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boatset_create_activity);
		
		mPager = (ViewPager)findViewById(R.id.boatset_pager);
		
		mPagerAdapter = new BoatsetPagerAdapter(getSupportFragmentManager());
		
		mPager.setAdapter(mPagerAdapter);
	}
	
	//This borrowed from android docs
	//http://developer.android.com/training/animation/screen-slide.html
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
}
