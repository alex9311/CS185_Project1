package edu.ucsb.cs.cs185.seatracing;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;

public class LineupsPagerAdapter extends FragmentStatePagerAdapter {

	Fragment emptyFragment;
	List<RacingSet> sets;
	
	public LineupsPagerAdapter(FragmentManager fm) {
		super(fm);
		sets = new ArrayList<RacingSet>();
	}

	@Override
	public Fragment getItem(int position) {
		if(position<0 || position>sets.size()){
			throw new IllegalArgumentException("LineupsPagerAdapter does not have page "+position+"!");
		}
		System.out.println("Tried to get object at position: "+position);

		if(position==sets.size()){
			return new EmptyAddRacingSetFragment();
		}
		else{
			LineupsFragment frag = new LineupsFragment();
			Bundle bndl = new Bundle();
			sets.get(position).writeToBundle(bndl);
			frag.setArguments(bndl);
			return frag;
		}
	}

	@Override
	public int getCount() {
		return sets.size()+1;
	}
	
	@Override
	public int getItemPosition(Object object){
		System.out.println("Tried to get position of object: "+object);
		return POSITION_NONE;
		//return sets.indexOf(object);
	}

	public void addNewSet(Bundle lineup) {
		sets.add(new RacingSet(lineup));
		notifyDataSetChanged();
	}

}
