package edu.ucsb.cs.cs185.seatracing.model;

import java.util.ArrayList;
import java.util.List;

public class Round {
	
	private static final int[] switches = {
		0, 1, 0, 2, 0, 1, 0, 3, 0, 1, 0, 2, 0, 1, 0
	};
	
	private long dateCreated;
	private int mNumRowers;
	private int mNumRaces;
	private int mNumBoats;
	private boolean switchingLast;
	
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
		if(switchingLast){
			mNumRaces = (int)Math.pow(2, mNumRowers);
		}
		else{
			mNumRaces = (int)Math.pow(2, (mNumRowers/mNumBoats)-1);
		}
		results = new ArrayList<Result>(mNumRaces);
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
	
	public void setSwitchingLast(boolean sl){
		switchingLast = sl;
	}
	
	public boolean switchingLast(){
		return switchingLast;
	}
	
	public int getNumRaces(){
		return this.mNumRaces;
	}
	
	
	/**
	 * 
	 * @param raceNum  Num of the race run just BEFORE this switch (0 index, 14 max)
	 * @param switchLast Whether the last pair is switching in this race.
	 * @return Which seat to switch next (0 index)
	 */
	public static int getSwitchIndex(int raceNum, boolean switchLast){
		//   / 1n: null
		//1y / 2n: 1
		//2y / 3n: 1, 2, 1
		//3y / 4n: 1, 2, 1, 3, 1, 2, 1
		
		return switches[raceNum];
	}
}
