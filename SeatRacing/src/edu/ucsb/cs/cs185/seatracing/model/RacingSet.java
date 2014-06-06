package edu.ucsb.cs.cs185.seatracing.model;

public class RacingSet {

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
}
