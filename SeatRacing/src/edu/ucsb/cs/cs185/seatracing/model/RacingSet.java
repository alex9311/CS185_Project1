package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class RacingSet implements Parcelable {
	
	private Boat mBoat1;
	private Boat mBoat2;

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
	
	public static List<RacingSet> convertParcelableList(ArrayList<Parcelable> setsIn){
		if(setsIn == null) return null;
		List<RacingSet> ret = new ArrayList<RacingSet>(setsIn.size());
		for(Parcelable p : setsIn){
			if( ! (p instanceof RacingSet)) throw new ClassCastException("Input list contains parcelables not of type RacingSet!");
			ret.add((RacingSet)p);
		}
		return ret;
	}
	
	public static ArrayList<RacingSet> getArrayList(List<RacingSet> setsIn){
		if(setsIn instanceof ArrayList<?>){
			return (ArrayList<RacingSet>)setsIn;
		}
		else{
			return new ArrayList<RacingSet>(setsIn);
		}
	}
	
	@Override
	public String toString(){
		return "Set: ["+mBoat1.toString()+", "+mBoat2.toString()+"]";
	}

}
