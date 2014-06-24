package edu.ucsb.cs.cs185.seatracing;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;

public class LineupsPagerAdapter extends FragmentStatePagerAdapter {

	boolean allowEditing=true;
	int highlightedSeat = -1;
	Fragment emptyFragment;
	List<RacingSet> mSets;
	
	public LineupsPagerAdapter(FragmentManager fm) {
		super(fm);
		mSets = new ArrayList<RacingSet>();
	}

	@Override
	public Fragment getItem(int position) {
		if(position<0 || position>mSets.size()){
			throw new IllegalArgumentException("LineupsPagerAdapter does not have page "+position+"!");
		}
		
		if(position==mSets.size() && allowEditing){
			return new EmptyAddRacingSetFragment();
		}
		else{
			LineupFragment frag = new LineupFragment();
			Bundle bndl = new Bundle();
			bndl.putParcelable("racingset", mSets.get(position));
			//mSets.get(position).writeToBundle(bndl);
			bndl.putInt("highlightedSeat", highlightedSeat);
			frag.setArguments(bndl);
			return frag;
		}
	}

	@Override
	public int getCount() {
		if(allowEditing){
			return mSets.size()+1;
		}
		else{
			return mSets.size();
		}
	}
	
	@Override
	public int getItemPosition(Object object){
		return POSITION_NONE;
		//return sets.indexOf(object);
	}

	public void addNewSet(RacingSet rs) {
		mSets.add(0,rs);
		notifyDataSetChanged();
	}
	
	public List<RacingSet> getRacingSets(){
		return mSets;
	}
	
	public void setRacingSets(List<RacingSet> sets){
		mSets = sets;
	}
	
	public void setHighlightedSeat(int index){
		highlightedSeat = index;
	}

	public void setEditable(boolean editable) {
		this.allowEditing = editable;
		notifyDataSetChanged();
	}

}
