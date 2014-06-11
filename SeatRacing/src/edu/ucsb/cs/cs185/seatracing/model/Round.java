package edu.ucsb.cs.cs185.seatracing.model;

import java.util.List;

public class Round {
	private long dateCreated;
	private int mNumRowers;
	private int mNumRaces;
	private int mNumBoats;
	
	private int currentRace;
	
	private List<RacingSet> mRacingSets;
	private List<Result> results;
	
	public Round(long dateCreatedIn){
		dateCreated = dateCreatedIn;
		currentRace=0;
	}
	
	public void setRacingSets(List<RacingSet> sets){
		mRacingSets = sets;
		mNumBoats = sets.size()*2;
		mNumRowers = mNumBoats*sets.get(0).getBoat1().size();
		mNumRaces = (int)Math.pow(2, mNumRowers);
	}
	
	public void setResults(List<Result> results){
		this.results = results;
	}
	
	public List<RacingSet> getRacingSets(){
		return this.mRacingSets;
	}
		
	public long dateCreated(){
		return this.dateCreated;
	}
	
	public int getCurrentRace(){
		return currentRace;
	}
	
	public void setCurrentRace(int race){
		currentRace = race;
	}
}
