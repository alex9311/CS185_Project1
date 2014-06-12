package edu.ucsb.cs.cs185.seatracing.model;

import android.os.Bundle;

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
	
	public void loadFromBundle(Bundle lineupBundle){
		roundID = lineupBundle.getInt("roundID");
		rowerID = lineupBundle.getInt("rowerID");
		boatID = lineupBundle.getInt("boatID");
		raceNum = lineupBundle.getInt("raceNum");
		time = lineupBundle.getLong("time");
		date = lineupBundle.getLong("date");
	}
	
	public void writeToBundle(int id, Bundle bundle){
		bundle.putInt("roundID",roundID);
		bundle.putInt("rowerID",rowerID);
		bundle.putInt("boatID",boatID);
		bundle.putInt("raceNum",raceNum);
		bundle.putLong("time", time);
		bundle.putLong("date",date);
	}

}
