package edu.ucsb.cs.cs185.seatracing;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BoatsetPagerAdapter extends FragmentStatePagerAdapter {
	
	public enum PairSelectState {
		PAIR,
		LINEUPS,
	};
	
	public static final int INDEX_NUM_PAIRS = 0;
	public static final int INDEX_SET_NAMES_1 = 1;
	public static final int INDEX_SET_NAMES_2 = 2;
	
	public static final int NUM_TOTAL_PAGES = 1;
	
	private PairSelectState state;
		
	List<Fragment> pages;

	public BoatsetPagerAdapter(FragmentManager fm) {
		super(fm);
		pages = new ArrayList<Fragment>(2);
		pages.add(new NumberPairsSelectFragment());
		state = PairSelectState.PAIR;
	}

	@Override
	public Fragment getItem(int arg0) {
		return pages.get(arg0);
	}
	
	@Override
	public int getItemPosition(Object item){
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return pages.size();
	}
	
	public PairSelectState getState(){
		return this.state;
	}
	
	public void switchToBoatPages(int numRowers){
		pages.clear();
		pages.add(BoatRowerNameFragment.newInstance(numRowers, 'A'));
		pages.add(BoatRowerNameFragment.newInstance(numRowers, 'B'));
		state = PairSelectState.LINEUPS;
		notifyDataSetChanged();
	}

}
