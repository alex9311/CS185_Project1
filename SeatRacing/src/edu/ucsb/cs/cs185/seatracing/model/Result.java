package edu.ucsb.cs.cs185.seatracing.model;

public class Result {
	private int roundID;
	private int rowerID;
	private int boatID;
	private int raceNum;
	private long time;
	private long date;
	
	public Result(){
		
	}
	
	public Result(int round, int rower, int boat, int raceIn, long timeIn, long dateIn){
		roundID = round;
		rowerID = rower;
		boatID = boat;
		raceNum = raceIn;
		time = timeIn;
		date = dateIn;
	}
	
	public void setRound(int round){
		this.roundID = round;
	}
	
	public void setRower(int rower){
		this.rowerID = rower;
	}
	
	public void setBoat(int boat){
		this.boatID = boat;
	}
	
	public void setRaceNum(int race){
		this.raceNum = race;
	}
	
	public void setTime(long time){
		this.time = time;
	}
	
	public void setDate(long date){
		this.date = date;
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
