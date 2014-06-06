package edu.ucsb.cs.cs185.seatracing;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BoatsetPagerAdapter extends FragmentStatePagerAdapter {
	
	public static final int INDEX_NUM_PAIRS = 0;
	public static final int INDEX_SET_NAMES_1 = 1;
	public static final int INDEX_SET_NAMES_2 = 2;
	
	public static final int NUM_TOTAL_PAGES = 1;
	
	private int numCurrentPages = 1;
	
	List<Fragment> pages;

	public BoatsetPagerAdapter(FragmentManager fm) {
		super(fm);
		pages = new ArrayList<Fragment>(3);
		pages.add(new NumberPairsSelectFragment());
	}

	@Override
	public Fragment getItem(int arg0) {
		return pages.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return numCurrentPages;
	}
	
	public void makeBoatOnePageAccessible(int numRowers){
		numCurrentPages++;
		pages.add(BoatRowerNameFragment.newInstance(numRowers, 'A'));
		notifyDataSetChanged();
	}
	
	public void makeBoatTwoPageAccessible(int numRowers){
		numCurrentPages++;
		pages.add(BoatRowerNameFragment.newInstance(numRowers, 'B'));
		notifyDataSetChanged();
	}

}
