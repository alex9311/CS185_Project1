package edu.ucsb.cs.cs185.seatracing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BoatsetCreateActivity extends FragmentActivity 
implements NumberPairsSelectListener, OnPageChangeListener {

	public static final int NEW_LINEUP = 1;

	private int numberPairs = 0;
	private int prevPage=0;
	private int nextPage=0;

	private ViewPager mPager;
	private BoatsetPagerAdapter mPagerAdapter;

	private Button prevButton;
	private Button nextButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boatset_create);

		mPager = (ViewPager)findViewById(R.id.boatset_pager);
		mPager.setOffscreenPageLimit(2);

		mPagerAdapter = new BoatsetPagerAdapter(getSupportFragmentManager());

		mPager.setAdapter(mPagerAdapter);

		prevButton = (Button)findViewById(R.id.prev_button);
		prevButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(prevPage);
			}
		});
		nextButton = (Button)findViewById(R.id.next_button);
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(nextPage==-1){
					//done, return from activity
					Intent result = new Intent();
					putLineupsData(result);
					setResult(RESULT_OK, result);
					finish();
				}
				else{
					mPager.setCurrentItem(nextPage);
				}
			}


		});

		prevButton.setClickable(false);
		nextButton.setClickable(false);

		mPager.setOnPageChangeListener(this);
	}
	
	private void putLineupsData(Intent intent){
		//TODO: put lineups info as extras into this intent to return them to main activity
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

	@Override
	public void numberPairsSelected(int numPairs) {
		numberPairs = numPairs;
		System.out.println("Selected: "+numPairs+" rowers!");
		mPagerAdapter.makeBoatOnePageAccessible(numPairs);
		//TODO: hold off on second page until first is done?
		mPagerAdapter.makeBoatTwoPageAccessible(numPairs);
		mPager.setCurrentItem(BoatsetPagerAdapter.INDEX_SET_NAMES_1);
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		//unused
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		//unused
	}

	@Override
	public void onPageSelected(int currPage) {
		//check for prev button
		System.out.println("Page "+currPage+" selected");
		if(currPage > 0){
			prevPage = currPage-1;
			prevButton.setClickable(true);
		}
		else{
			prevButton.setClickable(false);
		}

		//check for next button
		if(currPage < mPagerAdapter.getCount()-1){
			nextPage=currPage+1;
			nextButton.setClickable(true);
			nextButton.setText(getResources().getString(R.string.next_button));

		}
		else if(currPage == mPagerAdapter.getCount()-1){
			nextPage=-1;
			nextButton.setClickable(true);
			nextButton.setText(getResources().getString(R.string.done_button));
		}
		else{
			nextButton.setClickable(false);
			nextButton.setText(getResources().getString(R.string.next_button));
		}
	}
}
