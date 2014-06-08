package edu.ucsb.cs.cs185.seatracing;

import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class RacingPair {
	
	Rower r1;
	Rower r2;
	
	public RacingPair(Rower r1, Rower r2){
		this.r1 = r1;
		this.r2 = r2;
	}
	
	public Rower getRower1(){
		return r1;
	}
	
	public Rower getRower2(){
		return r2;
	}

}
