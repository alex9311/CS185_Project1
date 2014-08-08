package edu.ucsb.cs.cs185.seatracing.model;

import android.os.Bundle;

public class Result {
	private int roundID;
	private int rowerID;
	private int boatID;
	private int raceNum;
	private long time;
	private long date;
	private int seat;
	
	private String rower;
	
	
	public Result(){
		
	}
	
	public Result(Bundle bundle){
		loadFromBundle(bundle);
	}
	
	public Result(int round, int rower, int boat, int seat, int raceIn, long timeIn, long dateIn){
		roundID = round;
		rowerID = rower;
		boatID = boat;
		raceNum = raceIn;
		time = timeIn;
		date = dateIn;
	}
	
	public void setRower(String rower){
		this.rower = rower;
	}
	
	public void setRound(int roundId){
		this.roundID = roundId;
	}
	
	public void setRower(int rowerId){
		this.rowerID = rowerId;
	}
	
	public String getRower(){
		return this.rower;
	}
	
	public void setBoat(int boatId){
		this.boatID = boatId;
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
	
	public void setSeat(int seat){
		this.seat=seat;
	}
	
	public int seat(){
		return this.seat;
	}
	
	public int round(){
		return this.roundID;
	}
	
	public int rowerId(){
		return this.rowerID;
	}
	
	public int boatId(){
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
	
	public void loadFromBundle(Bundle lineupBundle){
		roundID = lineupBundle.getInt("roundID");
		rowerID = lineupBundle.getInt("rowerID");
		boatID = lineupBundle.getInt("boatID");
		raceNum = lineupBundle.getInt("raceNum");
		time = lineupBundle.getLong("time");
		date = lineupBundle.getLong("date");
		rower = lineupBundle.getString("rower");
		
	}
	
	public void writeToBundle(Bundle bundle){
		bundle.putInt("roundID",roundID);
		bundle.putInt("rowerID",rowerID);
		bundle.putInt("boatID",boatID);
		bundle.putInt("raceNum",raceNum);
		bundle.putLong("time", time);
		bundle.putLong("date",date);
		bundle.putString("rower",rower);
	}
	

}
