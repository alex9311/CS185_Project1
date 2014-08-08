package edu.ucsb.cs.cs185.seatracing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.ucsb.cs.cs185.seatracing.model.Boat;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;
import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class BoatsetCreateActivity extends FragmentActivity 
implements NumberPairsSelectListener, OnPageChangeListener, BoatLineupChangeListener {

	public static final int NEW_LINEUP = 1;



	private int numberPairs = -1;
	private boolean switchLast = false;
	private int prevPage=0;
	private int nextPage=0;

	private ViewPager mPager;
	private BoatsetPagerAdapter mPagerAdapter;

	private Button prevButton;
	private Button nextButton;
	
	RacingSet mRacingSet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boatset_create);
		
		/**
		 * In here, need to:
		 * 		- if not created, create adapter
		 * 		- set correct # pairs in adapter
		 *			if we are creating a new adapter, assume first time
		 * 		- set adapter to correct page
		 */

		mPager = (ViewPager)findViewById(R.id.boatset_pager);
		mPager.setOffscreenPageLimit(2);

		//==========
		//TODO: fix this for orientation changes
		Intent myIntent = getIntent();
		
		//If we are inflating for the first time
		if(savedInstanceState!=null && savedInstanceState.containsKey("numPairs")){
			numberPairs = savedInstanceState.getInt("numPairs", -1);
		}
		else if(mPagerAdapter == null && myIntent.hasExtra("numPairs")){ //if this is first set created
			numberPairs = myIntent.getIntExtra("numPairs", -1);
		}
		
		//create model objects if necessary
		if(numberPairs>0 && mRacingSet==null){
			Boat b1 = new Boat(null, numberPairs);
			b1.initBlankRowers();
			Boat b2 = new Boat(null, numberPairs);
			b2.initBlankRowers();
			mRacingSet = new RacingSet(b1, b2);
		}

		mPagerAdapter = new BoatsetPagerAdapter(getSupportFragmentManager(),mRacingSet);
		mPager.setAdapter(mPagerAdapter);
		//==========

		prevButton = (Button)findViewById(R.id.prev_button);
		prevButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(prevPage<0){
					onBackPressed();
				}
				mPager.setCurrentItem(prevPage);
			}
		});

		nextButton = (Button)findViewById(R.id.next_button);
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(nextPage<0){
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
		mPager.setOnPageChangeListener(this);
		
		if(savedInstanceState != null && savedInstanceState.containsKey("pagerPosition")){
			mPager.setCurrentItem(savedInstanceState.getInt("pagerPosition"));
			onPageSelected(mPager.getCurrentItem());
		}
		else{
			onPageSelected(0);
		}
	}

	private void putLineupsData(Intent intent){
		/*
		Boat b1 = new Boat(((BoatRowerNameFragment)mPagerAdapter.getItem(0)).getBoatName(), numberPairs);
		Boat b2 = new Boat(((BoatRowerNameFragment)mPagerAdapter.getItem(1)).getBoatName(), numberPairs);
		Rower[] b1Rowers = new Rower[numberPairs];
		Rower[] b2Rowers = new Rower[numberPairs];

		BoatRowerNameFragment frag = ((BoatRowerNameFragment)mPagerAdapter.getItem(0));
		for(int j=0; j<numberPairs; ++j){
			b1Rowers[j] = new Rower(frag.getRowerName(j));
		}
		frag = ((BoatRowerNameFragment)mPagerAdapter.getItem(1));
		for(int j=0; j<numberPairs; ++j){
			b2Rowers[j] = new Rower(frag.getRowerName(j));
		}

		b1.setRowers(b1Rowers);
		b2.setRowers(b2Rowers);

		RacingSet rs = new RacingSet(b1, b2);
		 */
		
		intent.putExtra("racingset", mRacingSet);
		intent.putExtra("switchLast", switchLast);
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
	public void numberPairsSelected(int numPairs, boolean switchLast) {
		numberPairs = numPairs;
		this.switchLast = switchLast;
		
		Boat b1 = new Boat(null, numberPairs);
		b1.initBlankRowers();
		Boat b2 = new Boat(null, numberPairs);
		b2.initBlankRowers();
		mRacingSet = new RacingSet(b1, b2);

		mPagerAdapter.switchToBoatPages(mRacingSet);
		onPageSelected(0);
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
		System.out.println("Selected page: "+currPage);
		if(mPagerAdapter.getState() == BoatsetPagerAdapter.PairSelectState.PAIR){
			prevPage = -1;
			prevButton.setEnabled(false);
			nextButton.setEnabled(false);
		}
		else if(mPagerAdapter.getState() == BoatsetPagerAdapter.PairSelectState.LINEUPS){

			if(mPager.getCurrentItem()==0){
				prevPage = -1;
				prevButton.setText(R.string.prev_button);
				prevButton.setEnabled(false);

				nextPage=1;
				nextButton.setText(R.string.next_button);
				nextButton.setBackgroundResource(R.drawable.selectable_item_background);
				nextButton.setTextColor(getResources().getColorStateList(R.color.switchable_text_color));
				nextButton.setEnabled(true);
			}
			else{
				prevPage=0;
				prevButton.setText(R.string.prev_button);
				prevButton.setEnabled(true);

				nextPage=-1;
				nextButton.setBackgroundResource(R.drawable.confirm_selectable_item_background);
				nextButton.setTextColor(getResources().getColorStateList(R.color.switchable_text_color_inverted));
				nextButton.setText(R.string.done_button);
				nextButton.setEnabled(true);
			}
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle icicle){
		super.onSaveInstanceState(icicle);
		icicle.putInt("numPairs", numberPairs);
		icicle.putInt("pagerPosition", mPager.getCurrentItem());
	}

	@Override
	public void boatLineupChanges(int index, Boat b) {
		mRacingSet.setBoat(index, b);
	}
	
}
