package edu.ucsb.cs.cs185.seatracing;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class LineupsPagerAdapter extends FragmentStatePagerAdapter {

	List<Fragment> pages;
	
	public LineupsPagerAdapter(FragmentManager fm) {
		super(fm);
		pages = new ArrayList<Fragment>();
		pages.add(new EmptyAddRacingSetFragment());
	}

	@Override
	public Fragment getItem(int position) {
		if(position<0 || position>=pages.size()){
			throw new IllegalArgumentException("LineupsPagerAdapter does not have page "+position+"!");
		}
		
		return pages.get(position);
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	public void addNewSet() {
		// TODO This will eventually take a model object as argument, mock for now
		pages.add(1,new LineupsFragment());
		notifyDataSetChanged();
	}

}
