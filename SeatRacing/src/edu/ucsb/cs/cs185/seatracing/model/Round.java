package edu.ucsb.cs.cs185.seatracing.model;

public class Round {
	private int date;
	private int size;
	
	public Round(int dateIn, int sizeIn){
		date = dateIn;
		size = sizeIn;
	}
	
	public int date(){
		return this.date;
	}
	
	public int size(){
		return this.size;
	}
}
