package edu.ucsb.cs.cs185.seatracing.model;

public class Rower {
	private String mName;
	
	public Rower(){
	}
	
	public Rower(String name){
		mName = name;
	}
	
	public String name(){
		return this.mName;
	}
	
	public void setName(String s){
		this.mName=s;
	}
}
