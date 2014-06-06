package edu.ucsb.cs.cs185.seatracing;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BoatsetPagerAdapter extends FragmentStatePagerAdapter {
	
	int NUM_PAGES = 3;
	
	List<Fragment> pages;

	public BoatsetPagerAdapter(FragmentManager fm) {
		super(fm);
		pages = new ArrayList<Fragment>(3);
		pages.add(new NumberPairsSelectFragment());
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return NUM_PAGES;
	}

}
