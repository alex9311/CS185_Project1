package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class RacingSet implements Parcelable {
	
	private static final int[] switches = {
		0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0
	};
	
	
	private Boat mBoat1;
	private Boat mBoat2;
	
	public RacingSet(Bundle lineups){
		mBoat1 = new Boat(0, lineups);
		mBoat2 = new Boat(1, lineups);
	}
	
	public RacingSet(Boat b1, Boat b2){
		this.mBoat1 = b1;
		this.mBoat2 = b2;
	}
	
	public Boat getBoat1(){
		return this.mBoat1;
	}
	
	public Boat getBoat2(){
		return this.mBoat2;
	}
	
	
	public void writeToBundle(Bundle bundle){
		mBoat1.writeToBundle(0, bundle);
		mBoat2.writeToBundle(1, bundle);
	}
	
	public RacingPair[] getRacingPairs(){
		RacingPair[] sets = new RacingPair[mBoat1.size()];
		
		for(int i=0; i<sets.length; ++i){
			sets[i] = new RacingPair(mBoat1.getRower(i), mBoat2.getRower(i));
		}
		
		return sets;
	}
	
	/**
	 * 
	 * @param raceNum  Num of the race run just BEFORE this switch (0 index, 14 max)
	 * @param switchLast Whether the last pair is switching in this race.
	 * @return Which seat to switch next (0 index)
	 */
	public static int getSwitchIndex(int raceNum, boolean switchLast){
		//   / 1n: null
		//1y / 2n: 1
		//2y / 3n: 1, 2, 1
		//3y / 4n: 1, 2, 1, 3, 1, 2, 1
		
		return switches[raceNum];
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		mBoat1.writeToParcel(dest, flags);
		mBoat2.writeToParcel(dest, flags);		
	}
	
    public static final Parcelable.Creator<RacingSet> CREATOR = new Parcelable.Creator<RacingSet>() {
    	public RacingSet createFromParcel(Parcel in) {
    		return new RacingSet(in);
    	}

    	public RacingSet[] newArray(int size) {
    		return new RacingSet[size];
    	}
    };

	private RacingSet(Parcel in) {
		mBoat1 = (Boat) in.readParcelable(Boat.class.getClassLoader());
		mBoat2 = (Boat) in.readParcelable(Boat.class.getClassLoader());
	}
	
	public static Bundle writeSetsToBundle(Bundle bundle, List<RacingSet> sets){
		bundle.putInt("numSets", sets.size());
		bundle.putBoolean("startNow", true);
		for(int i=0; i<sets.size(); ++i){
			Bundle set = new Bundle();
			sets.get(i).writeToBundle(set);
			bundle.putBundle("set"+i, set);
		}

		return bundle;
	}
	
	public static List<RacingSet> readSetsFromBundle(Bundle bundle){
		int numSets = bundle.getInt("numSets");
		List<RacingSet> ret = new ArrayList<RacingSet>(numSets);

		//inflate two timers v
		for(int i=0; i<numSets; ++i){
			Bundle setBundle = bundle.getBundle("set"+i);
			RacingSet rs = new RacingSet(setBundle);
			ret.add(rs);
		}
		return ret;
	}

}
