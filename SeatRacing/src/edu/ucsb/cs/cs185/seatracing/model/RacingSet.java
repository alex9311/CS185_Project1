package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class RacingSet implements Parcelable {
		
	private Boat[] mBoats;

	public RacingSet(Boat b1, Boat b2){
		mBoats = new Boat[2];
		mBoats[0] = b1;
		mBoats[1] = b2;
	}
	
	public Boat getBoat1(){
		return mBoats[0];
	}
	
	public void setBoat1(Boat b){
		this.mBoats[0] = b;
	}
	
	public Boat getBoat2(){
		return this.mBoats[1];
	}
	
	public void setBoat2(Boat b){
		this.mBoats[1] = b;
	}
	
	public Boat[] getBoats(){
		return this.mBoats;
	}
	
	public void setBoat(int i, Boat b){
		if(i<0 || i>this.mBoats.length){
			throw new IndexOutOfBoundsException();
		}
		this.mBoats[i] = b;
	}

	public RacingPair[] getRacingPairs(){
		RacingPair[] sets = new RacingPair[mBoats[0].size()];
		
		for(int i=0; i<sets.length; ++i){
			sets[i] = new RacingPair(mBoats[0], mBoats[1], i);
		}
		
		return sets;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mBoats[0], flags);
		dest.writeParcelable(mBoats[1], flags);
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
		mBoats = new Boat[2];
		mBoats[0] = in.readParcelable(Boat.class.getClassLoader());
		mBoats[1] = in.readParcelable(Boat.class.getClassLoader());
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
		return "Set: ["+mBoats[0].toString()+", "+mBoats[1].toString()+"]";
	}

}
