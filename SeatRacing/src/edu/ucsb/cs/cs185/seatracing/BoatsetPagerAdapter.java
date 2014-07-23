package edu.ucsb.cs.cs185.seatracing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import edu.ucsb.cs.cs185.seatracing.model.Boat;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;

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

	int numPairs=-1;

	RacingSet mRacingSet;

	public BoatsetPagerAdapter(FragmentManager fm) {
		this(fm, null);
	}

	public BoatsetPagerAdapter(FragmentManager fm, RacingSet rs){
		super(fm);
		mRacingSet = rs;
		if(rs != null){
			switchToBoatPages(rs);
		}
		else{
			state = PairSelectState.PAIR;
			notifyDataSetChanged();
		}
	}

	@Override
	public Fragment getItem(int position) {
		if(state == PairSelectState.PAIR){
			return new NumberPairsSelectFragment();
		}
		else if(state == PairSelectState.LINEUPS){
			Fragment frag = new BoatRowerNameFragment();
			Bundle args = new Bundle();
			args.putInt("boatIndex", position);
			if(position==0){
				args.putParcelable("boat", mRacingSet.getBoat1());
			}else if(position==1){
				args.putParcelable("boat", mRacingSet.getBoat2());
			}
			frag.setArguments(args);
			return frag;
		}
		else{
			return null;
		}
	}

	@Override
	public int getItemPosition(Object item){
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		if(state==PairSelectState.PAIR){
			return 1;
		}
		else if(state==PairSelectState.LINEUPS){
			return 2;
		}
		else return 0;
	}

	public PairSelectState getState(){
		return this.state;
	}

	public void switchToBoatPages(RacingSet rs){
		state = PairSelectState.LINEUPS;
		mRacingSet = rs;
		notifyDataSetChanged();
	}
	
	public RacingSet getRacingSet(){
		return mRacingSet;
	}

}
