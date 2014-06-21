package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class RacingSet implements Parcelable {
	
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
			sets[i] = new RacingPair(mBoat1, mBoat2, i);
		}
		
		return sets;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mBoat1, flags);
		dest.writeParcelable(mBoat2, flags);
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
		mBoat1 = in.readParcelable(Boat.class.getClassLoader());
		mBoat2 = in.readParcelable(Boat.class.getClassLoader());
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
	
	@Override
	public String toString(){
		return "Set: ["+mBoat1.toString()+", "+mBoat2.toString()+"]";
	}

}
