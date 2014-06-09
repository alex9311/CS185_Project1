package edu.ucsb.cs.cs185.seatracing;

import edu.ucsb.cs.cs185.seatracing.R.string;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;
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
		onPageSelected(0);
	}

	private void putLineupsData(Intent intent){
		Bundle lineupBundle = new Bundle();
		lineupBundle.putInt("numRowers", numberPairs);
		lineupBundle.putString("boatAName", ((BoatRowerNameFragment)mPagerAdapter.getItem(0)).getBoatName());
		lineupBundle.putString("boatBName", ((BoatRowerNameFragment)mPagerAdapter.getItem(1)).getBoatName());
		for(int i=0; i<2; ++i){
			BoatRowerNameFragment frag = ((BoatRowerNameFragment)mPagerAdapter.getItem(i));
			for(int j=0; j<numberPairs; ++j){
				lineupBundle.putString("rower"+(i-1)+"-"+j+"Name", frag.getRowerName(j));
			}
		}
		
		intent.putExtra("lineup", lineupBundle);
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
		
		mPagerAdapter.switchToBoatPages(numPairs);
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
		System.out.println("Page "+currPage+" selected");
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
				nextButton.setEnabled(true);
			}
			else{
				prevPage=0;
				prevButton.setText(R.string.prev_button);
				prevButton.setEnabled(true);
				
				nextPage=-1;
				nextButton.setText(R.string.done_button);
				nextButton.setEnabled(true);
			}
		}
	}
}
