package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

public class Rower {
	private String mName;
	private List<Long> finishTimes;
	
	public Rower(String name){
		mName = name;
		finishTimes = new ArrayList<Long>();
	}
	
	public String name(){
		return this.mName;
	}
	
	public void setName(String s){
		this.mName=s;
	}
	
	public void addfinishTime(long time){
		finishTimes.add(time);
	}
	
	public long getFinishTime(int position){
		return finishTimes.get(position);
	}
	
	public int getNumTimes(){
		return finishTimes.size();
	}
}
