package edu.ucsb.cs.cs185.seatracing.model;

import android.os.Bundle;

public class RacingSet {

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
}
