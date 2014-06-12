package edu.ucsb.cs.cs185.seatracing.model;

public class BoatResult {
	public Boat boat;
	public long time;
	
	public BoatResult(){}
	public BoatResult(Boat b, long t){
		boat = b;
		time = t;
	}
}
