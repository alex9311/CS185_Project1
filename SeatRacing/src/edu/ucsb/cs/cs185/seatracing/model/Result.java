package edu.ucsb.cs.cs185.seatracing.model;

public class Result {
	private int roundID;
	private int rowerID;
	private int boatID;
	private int raceNum;
	private long time;
	private long date;
	
	public Result(int round, int rower, int boat, int raceIn, long timeIn, long dateIn){
		roundID = round;
		rowerID = rower;
		boatID = boat;
		raceNum = raceIn;
		time = timeIn;
		date = dateIn;
	}
	
	public int round(){
		return this.roundID;
	}
	
	public int rower(){
		return this.rowerID;
	}
	
	public int boat(){
		return this.boatID;
	}
	
	public int raceNum(){
		return this.raceNum;
	}
	
	public long time(){
		return this.time;
	}
	
	public long date(){
		return this.date;
	}

}
